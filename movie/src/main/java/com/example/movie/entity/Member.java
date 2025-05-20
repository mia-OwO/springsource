package com.example.movie.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "m_memeber")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString

@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;

    @Column(unique = true)
    private String email;
    private String password;
    private String nickname;

    @Enumerated(EnumType.STRING) // String형식으로
    private MemberRole memberRole; // 권한 포함

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
