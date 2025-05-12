package com.example.board.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.board.entity.Board;
import com.example.board.entity.QBoard;
import com.example.board.entity.QMember;
import com.example.board.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);

    }

    @Override
    public Page<Object[]> list(String type, String keyword, Pageable pageable) {
        log.info("SearchBoard");

        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;
        // SELECTb.bno,b.TITLE,b.CREATE_DATE,m.NAME FROM BOARDTBL b JOIN BOARDMEMBER m
        // ON b.MEMBER_ID =m.EMAIL;
        JPQLQuery<Board> query = from(board); // from
        query.leftJoin(member).on(board.member.eq(member)); // join

        // 댓글 개수
        // r.board_id = b.bno
        // (SELECT COUNT(r.RNO) FROM REPLY r WHERE r.board_id = b.bno GROUP BY
        // r.BOARD_ID) AS reply_cnt,
        // JPAExpressions: 서브 쿼리용
        JPQLQuery<Long> replCount = JPAExpressions.select(reply.rno.count())
                .from(reply)
                .where(reply.board.eq(board)).groupBy(reply.board);

        JPQLQuery<Tuple> tuple = query.select(board, member, replCount);

        log.info("===========");
        log.info(query);
        log.info("===========");

        // bno > 0
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(board.bno.gt(0L));

        if (type != null) {
            // 검색
            BooleanBuilder builder = new BooleanBuilder();
            if (type.contains("t")) {
                builder.or(board.title.contains(keyword));
            }
            if (type.contains("c")) {
                builder.or(board.content.contains(keyword));
            }
            if (type.contains("w")) {
                builder.or(board.member.name.contains(keyword));
            }

            // builder + booleanBuilder
            booleanBuilder.and(builder);
        }

        // tuple where에 추가
        tuple.where(booleanBuilder);

        // Sort 생성
        Sort sort = pageable.getSort();

        // sort기준이 여러개 일 수 있어서 foreach돌려
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            // 정해진 형식
            String prop = order.getProperty();
            PathBuilder<Board> orderBuilder = new PathBuilder<>(Board.class, "board");
            tuple.orderBy(new OrderSpecifier(direction, orderBuilder.get(prop)));
        });
        // ----------------------전체 리스트 + Sort 적용

        // page 처리..
        tuple.offset(pageable.getOffset()); // 처음 위치에서의 계산을 해줌
        // 10

        tuple.limit(pageable.getPageSize());
        // .. 페이지 처리

        // Tuple : 행하나를 담는 구조
        List<Tuple> result = tuple.fetch(); // 실행, 결과 반환

        // 전체 개수 fetchCount()
        long count = tuple.fetchCount();
        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Object[] getBoardByBno(Long bno) {
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board); // from
        query.leftJoin(member).on(board.member.eq(member)); // join
        query.where(board.bno.eq(bno));

        JPQLQuery<Long> replCount = JPAExpressions.select(reply.rno.count())
                .from(reply)
                .where(reply.board.eq(board)).groupBy(reply.board);

        JPQLQuery<Tuple> tuple = query.select(board, member, replCount);

        Tuple row = tuple.fetchFirst();
        return row.toArray();
    }

}
