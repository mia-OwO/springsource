package com.example.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.board.security.CustomLoginSuccessHandler;

@EnableMethodSecurity // controller에서 @PreAuthorize, @PostAuthorize 사용할거야
@EnableWebSecurity // "/sample/guest" 이런거 안 하고 어떤 권한 가지고
@Configuration // 환경설정 파일이얌
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
        // http.authorizeHttpRequests(authorize -> authorize
        // .requestMatchers("/", "/sample/guest").permitAll()
        // .requestMatchers("/sample/member").hasRole("USER")
        // .requestMatchers("/sample/admin").hasRole("ADMIN")
        // .anyRequest().authenticated())
        http.authorizeHttpRequests(authirize -> authirize
                .requestMatchers("/css/**", "/js/**", "/image/**").permitAll() // static있는거 다 열어
                .anyRequest().permitAll())

                // .httpBasic(Customizer.withDefaults());

                // .httpBasic(Customizer.withDefaults()); -> formLogin 안 할때 사용
                // Customizer.withDefaults() -> 기본으로 띄워$
                // .formLogin(Customizer.withDefaults()); // 시큐리티가 제공하는 기본 폼 페이지
                .formLogin(login -> login.loginPage("/member/login")
                        .successHandler(successHandler())
                        .permitAll());
        // 소셜 로그인도 할거임

        // . 으로 연결 안 할거면 http 다시 부르기
        http.logout(logout -> logout
                // sample member 컨트롤에 가라
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/"));

        http.rememberMe(remember -> remember.rememberMeServices(rememberMeServices));

        return http.build();
    }

    @Bean // = new 한 후 스프링 컨테니ㅓ가 관리
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CustomLoginSuccessHandler successHandler() {
        return new CustomLoginSuccessHandler();
    }

    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {

        RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("mykey", userDetailsService,
                encodingAlgorithm);
        rememberMeServices.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
        rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 7);
        return rememberMeServices;

    }

}
