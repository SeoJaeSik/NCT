package io.nexgrid.bizcoretemplate.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank
    @Size(max = 20, message = "USER ID는 20글자 이하입니다.")
    private String username; // 유저아이디

    @NotBlank
    private String password; // 비밀번호
}
