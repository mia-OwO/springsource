package com.example.relation.repository.team;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.relation.entity.team.Team;
import com.example.relation.entity.team.TeamMember;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
