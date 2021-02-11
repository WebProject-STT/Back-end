package SSTT.Backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberLoginDto {

    private Long id;
    private String signId; // 회원 로그인 아이디
    private String pwd; // 회원 비밀번호

}
