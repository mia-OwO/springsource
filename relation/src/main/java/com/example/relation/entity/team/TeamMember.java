package com.example.relation.entity.team;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 회원은 단 하나의 팀에 소속된다.(N쪽에 외래키 제약조건을 건다)

@ToString(exclude = "team") // team정보 찍지 마세용(관계가 있으면)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class TeamMember {
    // id, name(회원명)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String userName;

    // 외래키 필드명 지정
    // JoinColumn 미사용시 table명_pk명
    @JoinColumn(name = "team_id")
    @ManyToOne // --> 양방향 연관관계
    private Team team;

}
