package SSTT.Backend.repository;

import SSTT.Backend.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    // 회원 저장
    public void save(User user){
        em.persist(user);
    }

    // 아이디 조회
    public List<User> findBySignId(String signId){
        return em.createQuery("select u from User u where u.signId = :user_sign_id", User.class)
                .setParameter("user_sign_id", signId)
                .getResultList();
    }

    // 회원 번호 조회
    public User findOne(Long id) {
        return em.find(User.class, id);
    }
}
