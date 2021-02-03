package SSTT.Backend.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id; // 회원 번호

    @Column(name = "member_sign_id")
    private String signId; // 회원 로그인 아이디

    @Column(name = "member_name")
    private String name; // 회원 이름

    @Column(name = "member_email")
    private String email; // 회원 이메일

    @Column(name = "member_pwd")
    private String pwd; // 회원 비밀번호

    @Column(name = "member_signup_dt")
    private LocalDateTime signupDt; // 회원 가입일

    @OneToMany(mappedBy = "member")
    private List<Contents> contentsList = new ArrayList<>(); // 게시글 리스트


    @Builder
    public Member(Long id, String signId, String name, String email, String pwd, LocalDateTime signupDt, List<Contents> contentsList) {
        this.id = id;
        this.signId = signId;
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.signupDt = signupDt;
        this.contentsList = contentsList;
    }

}
