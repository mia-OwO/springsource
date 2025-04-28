package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.example.jpa.entity.Board;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, QuerydslPredicateExecutor<Board> {

    // // where b.writer='user4'
    // List<Board> findByWriter(String writer);

    // List<Board> findByTitle(String title);

    // // b.writer like 'user4%'
    // List<Board> findByWriterStartingWith(String writer);

    // // b.writer like '%user4'
    // List<Board> findByWriterEndingWith(String writer);

    // // b.writer like '%user4%'
    // List<Board> findByWriterContaining(String writer);

    // // b.WRITER like '%user%' or b.content like '%내용%'
    // List<Board> findByWriterContainingOrContentContaining(String writer, String
    // content);

    // // b.WRITER like '%user%' and b.content like '%내용%'
    // List<Board> findByWriterContainingAndContentContaining(String writer, String
    // content);

    // // bno > 5 게시물 조회
    // List<Board> findByBnoGreaterThan(Long bno);

    // // bno >0 order by bno desc
    // List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno);

    // // bno >= 5 and bno <= 10
    // List<Board> findByBnoBetween(Long start, Long end);

    // -------------------------------------------------
    // @Query
    // 별칭 꼭 사용, *대신 별칭
    // from -> entity클래스 명 기준(테이블 명 x)
    // -------------------------------------------------

    // @Query("select b from Board b where b.writer = ?1")
    @Query("select b from Board b where b.writer = :writer")
    // List<Board> findByWriter(String writer);
    List<Board> findByWriter(@Param("writer") String writer);

    @Query("select b from Board b where b.writer like  ?1%")
    List<Board> findByWriterStartingWith(String writer);

    @Query("select b from Board b where b.writer like  %?1%")
    List<Board> findByWriterContaining(String writer);

    // @Query("select b from Board b where b.bno >?1") -> 주로 사용

    // value = , nativeQuery = true --> entity명 x 실제 사용하는 sql 구문 형식 사용

    @Query("select b.title, b.writer from Board b where b.title like  %?1% ")
    List<Object[]> findByTitle2(String title);

    // sql 구문 형식 사용
    // @Query(value = "select * from Board b where b.bno >?1", nativeQuery = true)

    // NativeQuery -> 잘 사용하지 않음
    @NativeQuery("select * from Board b where b.bno >?1")
    List<Board> findByBnoGreaterThan(Long bno);
}
