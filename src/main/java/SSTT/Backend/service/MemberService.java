package SSTT.Backend.service;

import SSTT.Backend.domain.Member;
import SSTT.Backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor // final 선언과 관련된 lombok 기능
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    public Long join(Member member){
        validateDuplicateUser(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 검증
    private void validateDuplicateUser(Member member) {
        // 문제 발생 시 EXCEPTION 발생
        List<Member> findMembers = memberRepository.findBySignId(member.getSignId());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    // 로그인

    // 로그아웃
}
