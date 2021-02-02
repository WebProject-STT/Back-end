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
    private Long user_idx; // 회원 번호

    private String user_id; // 회원 아이디
    private String user_name; // 회원 이름
    private String user_email; // 회원 이메일
    private String user_pwd; // 회원 비밀번호
    private LocalDateTime user_signup_dt; // 회원 가입일

    @OneToMany(mappedBy = "user")
    private List<Contents> contentsList = new ArrayList<>(); // 게시글 리스트
}
