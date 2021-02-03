package SSTT.Backend.config;

import SSTT.Backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 활성화 어노테이션
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final MemberService memberService;
    
    @Bean
    public PasswordEncoder passwordEncoder() { // 비밀번호 암호화 인코더
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest().permitAll() // 누구나 접근 허용
                .and()
                    .formLogin()
                        .loginPage("/login") // 로그인 페이지
                        .defaultSuccessUrl("/") // 로그인 후 리다이렉트할 주소
                .and()
                    .logout()
                        .logoutSuccessUrl("/login") // 로그아웃 성공 후 리다이렉트할 주소
                        .invalidateHttpSession(true); // 로그아웃 후 세션 전체 삭제
    }

    // 로그인 할 때 필요한 정보를 가져옴
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
