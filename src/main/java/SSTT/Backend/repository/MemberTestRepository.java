package SSTT.Backend.repository;

import SSTT.Backend.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberTestRepository {

    private final EntityManager em;

    // 회원 저장
    public void save(Member member){
        em.persist(member);
    }

    // 아이디 조회
    public List<Member> findBySignId(String signId){
        return em.createQuery("select m from Member m where m.signId = :member_sign_id", Member.class)
                .setParameter("member_sign_id", signId)
                .getResultList();
    }

    // 회원 번호 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }
}
