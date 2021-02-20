package SSTT.Backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberLoginSuccessDto {
    private Long id;
    private String name;
}
