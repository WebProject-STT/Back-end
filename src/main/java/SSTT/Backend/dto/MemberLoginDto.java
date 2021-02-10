package SSTT.Backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberLoginDto {

    private Long id;
    private String signId; // 회원 로그인 아이디
    private String pwd; // 회원 비밀번호

    @Builder
    public MemberLoginDto(Long id, String signId, String pwd) {
        this.id = id;
        this.signId = signId;
        this.pwd = pwd;
    }

}
