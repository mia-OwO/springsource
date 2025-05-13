package com.example.board.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@Getter
public class AuthMemberDTO extends User { // User을 extends해야 관리해줌

    private String email;

    private String name;

    private String password;
    private Boolean fromSocial;

    // username -> id 개념(실제 이름이 아님)

    public AuthMemberDTO(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.email = username;
        this.password = password;
        this.fromSocial = fromSocial;

    }

}
