package SSTT.Backend.service;

import SSTT.Backend.domain.Member;
import SSTT.Backend.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(value = false) // false로 지정하면 db에 commit 된 결과 확인 가능
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setSignId("sb2504");

        // when
        Long savedId = memberService.join(member);

        // then
        assertEquals(member, memberRepository.findOne(savedId)); // 같은 아이디인지 확인을 통해 저장되었음을 확인한다.

    }

    @Test(expected = IllegalStateException.class) // 테스트 결과가 exception 이어야 함
    public void 중복회원예외() throws Exception {

        // given
        Member member1 = new Member();
        member1.setSignId("2504sb");

        Member member2 = new Member();
        member2.setSignId("2504sb");

        // when
        memberService.join(member1);
        memberService.join(member2); // 에러가 발생해야 함

        // then
        fail("에러가 발생해야 한다."); // 여기로 오면 안됨! 에러를 못잡은 것! ㅠ

    }
}