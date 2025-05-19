package com.example.movie.dto;

import java.lang.reflect.Member;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@Getter
public class AuthMemberDTO extends User { // User을 extends해야 관리해줌

    private MemberDTO memberDTO;

    // username -> id 개념(실제 이름이 아님)

    public AuthMemberDTO(String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

    }

    public AuthMemberDTO(MemberDTO memberDTO) {
        super(memberDTO.getEmail(), memberDTO.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + memberDTO.getMemberRole())));
        this.memberDTO = memberDTO; // new AuthMemberDTO(memberDTO); 이거 호출
    }

}
