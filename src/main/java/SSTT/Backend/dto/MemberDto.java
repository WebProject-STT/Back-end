package SSTT.Backend.dto;

import SSTT.Backend.domain.Contents;
import SSTT.Backend.domain.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    private Long id;
    private String signId; // 회원 로그인 아이디
    private String name; // 회원 이름
    private String email; // 회원 이메일
    private String pwd; // 회원 비밀번호
    private LocalDateTime signupDt; // 회원 가입일
//    private List<Contents> contentsList = new ArrayList<>(); // 게시글 리스트

    // Member 객체로 변환
    public Member toEntity() {
        return Member.builder()
                .id(id)
                .signId(signId)
                .name(name)
                .email(email)
                .pwd(pwd)
                .signupDt(signupDt)
                .build();
    }

}
