package SSTT.Backend.service;

import SSTT.Backend.domain.User;
import SSTT.Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor // final 선언과 관련된 lombok 기능
public class UserService {

    private final UserRepository userRepository;

    // 회원 가입
    public Long join(User user){
        validateDuplicateUser(user); // 중복 회원 검증
        userRepository.save(user);
        return user.getId();
    }

    // 중복 회원 검증
    private void validateDuplicateUser(User user) {
        // 문제 발생 시 EXCEPTION 발생
        List<User> findUsers = userRepository.findBySignId(user.getSignId());
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public User findOne(Long userId){
        return userRepository.findOne(userId);
    }

    // 로그인

    // 로그아웃
}
