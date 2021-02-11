package SSTT.Backend.controller;

import SSTT.Backend.config.security.JwtTokenProvider;
import SSTT.Backend.domain.Member;
import SSTT.Backend.dto.MemberLoginDto;
import SSTT.Backend.dto.MemberSignUpDto;
import SSTT.Backend.repository.MemberRepository;
import SSTT.Backend.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @ApiOperation(value = "회원 가입", notes = "회원 번호 반환. 아이디 중복 시 -1 (long) 반환")
    @PostMapping("/user/signup")
    public Long signup(@RequestBody @Valid MemberSignUpDto member) {
        try {
            memberService.validateDuplicateMember(member.getSignId());
        } catch (IllegalArgumentException e) {
            System.out.println("아이디 중복 오류");
            long result = -1;
            return result;
        }
        return memberRepository.save(
                Member.builder()
                        .signId(member.getSignId())
                        .name(member.getName())
                        .pwd(passwordEncoder.encode(member.getPwd()))
                        .email(member.getEmail())
                        .roles(Collections.singletonList("USER")) // 최초 가입 시 USER로 설정
                        .signupDt(LocalDateTime.now())
                        .build()).getId();
    }

    @ApiOperation(value = "로그인", notes = "회원 번호 + ' ' + 토큰 반환. 아이디 또는 패스워드 오류 시 false (string) 반환")
    @PostMapping("/user/login")
    public String login(@RequestBody @Valid MemberLoginDto user) {
        Member member;
        try {
            member = memberService.findSignId(user.getSignId());
            if (!passwordEncoder.matches(user.getPwd(), member.getPassword())) {
                throw new IllegalArgumentException("잘못된 비밀번호입니다.");
            }
        } catch (IllegalArgumentException e) {
            return "false";
        }


        return  jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }
}
