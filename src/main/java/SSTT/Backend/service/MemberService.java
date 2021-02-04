package SSTT.Backend.service;

import SSTT.Backend.domain.Member;
import SSTT.Backend.domain.Role;
import SSTT.Backend.dto.MemberDto;
import SSTT.Backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor // final 선언과 관련된 lombok 기능
public class MemberService implements UserDetailsService{

    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public Long signUp(MemberDto memberDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPwd(passwordEncoder.encode(memberDto.getPwd()));
        memberDto.setSignupDt(LocalDateTime.now());

        return memberRepository.save(memberDto.toEntity()).getId();
    }

    public void idCheck(String signId) {
        Optional<Member> findMembers = memberRepository.findBySignId(signId);
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String signId) throws UsernameNotFoundException {
        // 로그인을 하기 위해 가입된 user 정보 조회
        Optional<Member> memberWrapper = memberRepository.findBySignId(signId);
        Member member = memberWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        // (간단하게!!) signId이 admin이면 admin 권한 부여. 아니면 member 권한 부여
        if("admin".equals(signId)){
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority((Role.MEMBER.getValue())));
        }

        // 아이디, 비밀번호, 권한리스트를 매개변수로 User를 만들어 반환
        return new User(member.getSignId(), member.getPwd(), authorities);
    }
    
//    // 회원 가입
//    public Long join(Member member){
//
//        validateDuplicateUser(member); // 중복 회원 검증
//
//        memberRepository.save(member);
//        return member.getId();
//    }
//
//    // 중복 회원 검증
//    private void validateDuplicateUser(Member member) {
//        // 문제 발생 시 EXCEPTION 발생
//        List<Member> findMembers = memberRepository.findBySignId(member.getSignId());
//        if (!findMembers.isEmpty()) {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        }
//    }
//
//    public Member findOne(Long memberId){
//        return memberRepository.findOne(memberId);
//    }

}
