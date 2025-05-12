package com.example.security.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.entity.ClubMember;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {
    // email과 fromSocial 여부 확인
    // roleSet -> Clubmember의 private Set<ClubMemberRole> roleSet = new HashSet<>();
    // Transcational -> left join구문으로 처리하려면 > @EntityGraph(attributePaths = {
    // "roleSet"
    // }, type = EntityGraph.EntityGraphType.LOAD) 사용
    // --> Transactional 안 사용해도 됨
    @EntityGraph(attributePaths = { "roleSet" }, type = EntityGraph.EntityGraphType.LOAD)

    ClubMember findByEmailAndFromSocial(String email, boolean fromSocial);
}
