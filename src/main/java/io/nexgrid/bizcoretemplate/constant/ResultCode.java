package io.nexgrid.bizcoretemplate.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

	// 성공 응답 목록
	SUCCESS("0000", "성공적으로 처리되었습니다."),
	NOT_FOUND_DATA("0001", "조회된 정보가 없습니다."),

	// 실패 응답 목록
	MEMBER_NOT_FOUND("1000", "존재하지 않는 사용자입니다."),
	UNAUTHORIZED("1001", "인증되지 않은 사용자입니다."),
	ACCESS_DENIED("1002", "접근 권한이 없습니다."),
	AUTHENTICATION_FAILED("1003", "ID 또는 PW가 일치하지 않습니다."),
	DUPLICATE_ID("1004", "이미 존재하는 유저 ID 입니다."),
	NOT_VALID_PARAMETER("1005", "파라미터 값이 부적절합니다."),
	PARAMETER_MISSING("1006", "필수 파라미터가 누락되었습니다."),
	INVALID_REQUEST_FORMAT("1007", "잘못된 형식으로 요청하였습니다."),
	LOCKED_ACCOUNT("1008", "잠겨있는 계정입니다."),
	DISABLED_ACCOUNT("1009", "비활성화된 계정입니다."),
	EXPIRED_ACCOUNT("1010", "유효기간이 만료된 계정입니다."),
	EXPIRED_PASSWORD("1011", "계정의 비밀번호가 만료되었습니다."),
	DATABASE_ERROR("7000", "DB 연동 에러가 발생하였습니다."),
	INTERNAL_SERVER_ERROR("9998", "내부 오류가 발생하였습니다."),
	FAIL("9999", "정의되지 않은 오류입니다.");

	private String code;
	private String message;

}