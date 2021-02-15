package SSTT.Backend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity // JPA Entity 임을 알림
@Getter // Getter 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 인자 없는 생성자를 자동 생성
@AllArgsConstructor // 인자를 갖춘 생성자를 자동 생성
@Builder
// UserDetails 객체를 통해 권한 정보를 관리하기 때문에 재정의를 통해서 사용
// Entity 와 UserDetails 를 구분하여 사용할 수도 있으나 여기서는 같은 클래스에서 관리하는 방법 사용
public class Member implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id; // 회원 번호 (pk)

    @Column(name = "member_sign_id", nullable = false, unique = true)
    private String signId; // 회원 로그인 아이디

    @Column(name = "member_name", nullable = false)
    private String name; // 회원 이름

    @Column(name = "member_email", nullable = false)
    private String email; // 회원 이메일

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Json 결과로 출력하지 않을 데이터 표시
    @Column(name = "member_pwd", nullable = false)
    private String pwd; // 회원 비밀번호

    @Column(name = "member_signup_dt")
    private LocalDateTime signupDt; // 회원 가입일

//    @OneToMany(mappedBy = "member")
//    private List<Category> categoryList = new ArrayList<>(); // 카테고리 리스트

//    @OneToMany(mappedBy = "member")
//    private List<Contents> contentsList = new ArrayList<>(); // 게시글 리스트


    // Role List
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    // UserDetails 를 implements 하여 생성된 함수들
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return signId;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getPassword() {
        return pwd;
    }

    // Security 에서 사용하는 회원 상태 값인데, 모두 사용하지 않으므로 true 로 설정
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() { // 계정이 만료 안되었는지
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() { // 계정이 잠기지 않았는지
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() { // 계정 패스워드가 만료 안되었는지
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() { // 계정이 사용 가능한지
        return true;
    }
}
