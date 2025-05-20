package com.example.movie.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.movie.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

    @Modifying // query쓸때 무조건 추가하기
    @Query("UPDATE Member m SET m.nickname = :nickname WHERE m.email = :email")
    void updateNickname(String nickname, String email);
}
