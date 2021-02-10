package SSTT.Backend.dto;

import SSTT.Backend.domain.Contents;
import SSTT.Backend.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberSignUpDto {

    private Long id;
    private String signId; // 회원 로그인 아이디
    private String name; // 회원 이름
    private String email; // 회원 이메일
    private String pwd; // 회원 비밀번호
    private LocalDateTime signupDt; // 회원 가입일

    @Builder
    public MemberSignUpDto(Long id, String signId, String name, String email, String pwd, LocalDateTime signupDt) {
        this.id = id;
        this.signId = signId;
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.signupDt = signupDt;
    }
}
