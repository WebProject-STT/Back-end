package SSTT.Backend.service;

import SSTT.Backend.domain.Member;
import SSTT.Backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor // final 선언과 관련된 lombok 기능
public class MemberService implements UserDetailsService{

    private final MemberRepository memberRepository;

    // 아이디 중복 조회
    public void validateDuplicateMember(String signId) {
        Optional<Member> findMember = memberRepository.findBySignId(signId);
        if (!findMember.isEmpty()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 정보 조회를 signId 이용
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findBySignId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    // 회원 아이디 조회
    public Member findSignId(String signId){
        return memberRepository.findBySignId(signId)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID 입니다."));
    }

    // 회원 번호 조회
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID 입니다."));
    }
}
