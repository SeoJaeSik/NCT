package io.nexgrid.bizcoretemplate.dto;

import io.nexgrid.bizcoretemplate.domain.member.Member;
import io.nexgrid.bizcoretemplate.domain.member.enums.Gender;
import io.nexgrid.bizcoretemplate.domain.member.enums.Role;
import io.nexgrid.bizcoretemplate.domain.member.enums.UserStatus;
import lombok.Data;

@Data
public class SessionDto {

    private String sessionId;
    private Member member;

    private Long id; // Sequence = Primary Key
    private String username; // 유저아이디
    private String password; // 비밀번호
    private String name; // 성명
    private String birth; // 생년월일 (YYYYMMDD)
    private Gender gender; // 성별 (MALE-남, FEMALE-여, NONE-식별불가)
    private Role role; // 유저 권한 (NORMAL-유저, ROOT-관리자)

    private String domain; // 도메인명 (접속기록용)
    private Integer loginFailCount; // 로그인 실패횟수
    private UserStatus userStatus; // 유저 상태 (ACTIVE-활동, IN_ACTIVE-휴먼, LOCKED-잠김)
    private String passwordModifiedDate; // 비밀번호 변경날짜
    private String lastLoginDate; // 마지막 로그인 날짜

}
