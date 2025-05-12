package com.example.security;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.security.entity.ClubMember;
import com.example.security.entity.ClubMemberRole;
import com.example.security.repository.ClubMemberRepository;

@SpringBootTest
public class SecurityTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClubMemberRepository clubMemberRepository;

    // @Transactional
    @Test
    public void testRead() {
        ClubMember clubMember = clubMemberRepository.findByEmailAndFromSocial("user1@gmail.com", false);
        System.out.println(clubMember);

    }

    @Test
    public void testInsert() {
        // 모든 회원은 USER권한 부여
        // 9 회원은 MANAGER 권한 부여
        // 10 회원은 ADMIN 권한 부여
        IntStream.rangeClosed(1, 10).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .email("user" + i + "@gmail.com")
                    .name("user" + i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            clubMember.addMemberRole(ClubMemberRole.USER);

            if (i > 8) {
                clubMember.addMemberRole(ClubMemberRole.MANAGER);
            }
            if (i > 9) {
                clubMember.addMemberRole(ClubMemberRole.ADMIN);
            }
            clubMemberRepository.save(clubMember);
        });
    }

    // @Transactional
    @Test
    public void testEncoder() {
        // 원 비밀번호 : rawpassword
        String password = "1111";

        // passwordEncoder.encode(원비밀번호) : 암호화
        // -> {bcrypt}$2a$10$1gaQVcPHqzDCmBxg1rW6Hu/ieMPtFV7EcOvRf/Q43Afg6CijpN8tm
        String encodePassword = passwordEncoder.encode(password); // 암호화
        System.out.println("password " + password + " , " + "encodePassword: " + encodePassword);

        System.out.println("비밀번호 오류" + passwordEncoder.matches("2222", encodePassword)); // 비번비교(matches)
        System.out.println(passwordEncoder.matches("1111", encodePassword));
    }

}
