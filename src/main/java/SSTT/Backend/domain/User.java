package SSTT.Backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id; // 회원 번호

    @Column(name = "user_sign_id")
    private String signId; // 회원 로그인 아이디

    @Column(name = "user_name")
    private String name; // 회원 이름

    @Column(name = "user_email")
    private String email; // 회원 이메일

    @Column(name = "user_pwd")
    private String pwd; // 회원 비밀번호

    @Column(name = "user_signup_dt")
    private LocalDateTime signupDt; // 회원 가입일

    @OneToMany(mappedBy = "user")
    private List<Contents> contentsList = new ArrayList<>(); // 게시글 리스트
}
