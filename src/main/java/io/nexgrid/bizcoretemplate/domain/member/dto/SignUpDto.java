package io.nexgrid.bizcoretemplate.domain.member.dto;

import io.nexgrid.bizcoretemplate.domain.member.Member;
import io.nexgrid.bizcoretemplate.domain.member.enums.Gender;
import io.nexgrid.bizcoretemplate.domain.member.enums.Role;
import io.nexgrid.bizcoretemplate.domain.member.enums.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpDto {

    @NotBlank @Size(max = 20, message = "USER ID는 20글자 이하입니다.")
    private String username; // 유저아이디

    @NotBlank
    private String password; // 비밀번호

    @NotBlank @Pattern(regexp = "^[가-힣]{2,5}$")
    private String name; // 성명

    @NotBlank @Pattern(regexp = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])")
    private String birth; // 생년월일 (YYYYMMDD)

    @NotBlank @Pattern(regexp = "^(MALE|FEMALE|NONE)$")
    private String gender; // 성별 (MALE-남, FEMALE-여, NONE-식별불가)

    @NotBlank @Pattern(regexp = "^(NORMAL|ROOT)$")
    private String role; // 유저 권한 (NORMAL-유저, ROOT-관리자)

    public Member signUpEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .name(name)
                .birth(birth)
                // Enum 값 검증시 Enum 에 선언되어있지 않은 필드로 요청시 IllegalArgumentException 발생 - 정규표현식으로 하드코딩으로 적용(enum값 변경시 변경요함)
                .gender(Gender.valueOf(gender))
                .role(Role.valueOf(role))
                .userStatus(UserStatus.ACTIVE)
                .build();
    }

}
