package SSTT.Backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSignUpDto {

    private Long id;
    private String signId; // 회원 로그인 아이디
    private String name; // 회원 이름
    private String email; // 회원 이메일
    private String pwd; // 회원 비밀번호
    private LocalDateTime signupDt; // 회원 가입일

}
