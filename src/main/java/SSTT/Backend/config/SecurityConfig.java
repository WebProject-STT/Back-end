package SSTT.Backend.config;

import SSTT.Backend.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // config bean이라는 것을 명시
@EnableWebSecurity // spring security config를 할 클래스임을 명시
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 인증을 무시하기 위한 설정 (static)
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http로 들어오는 요청에 대한 보안 구성
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()//로그인 설정
                .loginPage("/login")// 커스텀 로그인 페이지 사용
                .defaultSuccessUrl("/") // 로그인 성공 시 이동할 페이지
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true) // 세션 초기화
                .and()
                .exceptionHandling()
                .and()
                .csrf().disable(); // 프론트에 토큰값을 보내기 위해 csrf 보안 설정 비활성화
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 로그인 처리를 하기 위한 설정
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}