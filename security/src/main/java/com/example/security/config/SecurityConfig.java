package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration // 환경설정 파일이얌
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/sample/guest").permitAll()
                .requestMatchers("/sample/member").hasRole("USER")
                .requestMatchers("/sample/admin").hasRole("ADMIN")
                .anyRequest().authenticated())
                // .httpBasic(Customizer.withDefaults());

                // .httpBasic(Customizer.withDefaults()); -> formLogin 안 할때 사용
                // Customizer.withDefaults() -> 기본으로 띄워$
                // .formLogin(Customizer.withDefaults()); // 시큐리티가 제공하는 기본 폼 페이지
                .formLogin(login -> login.loginPage("/member/login").permitAll()

                );

        // . 으로 연결 안 할거면 http 다시 부르기
        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/"));

        return http.build();
    }

    @Bean // = new 한 후 스프링 컨테니ㅓ가 관리
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // DB가 없어서 했던것 -> db들어와서 주석 처리함
    // @Bean
    // UserDetailsService users() {
    // UserDetails user = User.builder()
    // .username("user")
    // .password("{bcrypt}$2a$10$9Pz7H8/ssXGc97bIS3d//.01X9evuJDzKuVmYlfNPG1k4RbI9Xk7u")
    // .roles("USER") // ROLE_권한명 부여(user)
    // .build();

    // UserDetails admin = User.builder()
    // .username("admin")
    // .password("{bcrypt}$2a$10$9Pz7H8/ssXGc97bIS3d//.01X9evuJDzKuVmYlfNPG1k4RbI9Xk7u")
    // .roles("USER", "ADMIN") // admin은 USER, ADMIN권한 을 가지고 있음 -> member에 로그인 가능
    // .build();
    // return new InMemoryUserDetailsManager(user, admin);

    // }
}
