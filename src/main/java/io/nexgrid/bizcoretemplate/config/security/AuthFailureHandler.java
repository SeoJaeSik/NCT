package io.nexgrid.bizcoretemplate.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nexgrid.bizcoretemplate.constant.ResultCode;
import io.nexgrid.bizcoretemplate.dto.ResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;

/**
 *  security 로그인/인증 실패관련 처리 핸들러
 */
@Slf4j
@Configuration
public class AuthFailureHandler implements AuthenticationFailureHandler {

    private final HttpServletResponse httpServletResponse;

    public AuthFailureHandler(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorCode = null;
        String errorMsg = null;

        if(exception instanceof UsernameNotFoundException){                     // 계정정보가 없을때
            errorCode = ResultCode.MEMBER_NOT_FOUND.getCode();
            errorMsg = ResultCode.MEMBER_NOT_FOUND.getMessage();
            log.info("### Login Fail : ErrorCode - {}, {}", ResultCode.MEMBER_NOT_FOUND.getCode(), ResultCode.MEMBER_NOT_FOUND.getMessage());
        }
        if(exception instanceof AuthenticationServiceException){                // 인증 요구가 거부됐을 때 (DB 연동오류)
            errorCode = ResultCode.DATABASE_ERROR.getCode();
            errorMsg = ResultCode.DATABASE_ERROR.getMessage();
            log.info("### Login Fail : ErrorCode - {}, {}", ResultCode.DATABASE_ERROR.getCode(), ResultCode.DATABASE_ERROR.getMessage());
        }

        /*  로그인시에 계정정보가 없을시 CustomUserDetailService 에서 UsernameNotFoundException 를 발생시키는데
        *   DaoAuthenticationProvider 에서 security 보안상의 이유로 내부적으로 BadCredentialsException 으로 변환시킴
        *   UsernameNotFoundException 사용하려면 커스텀으로 구현해야함
        *   해당 프로젝트에서는 계정이없는지, PW가 틀렸는지는 로그로 찍어서 판별
        * */
        if(exception instanceof BadCredentialsException){                       // 비밀번호가 일치하지 않을 때
            errorCode = ResultCode.AUTHENTICATION_FAILED.getCode();
            errorMsg = ResultCode.AUTHENTICATION_FAILED.getMessage();
            log.info("### Login Fail : ErrorCode - {}, {}", ResultCode.AUTHENTICATION_FAILED.getCode(), ResultCode.AUTHENTICATION_FAILED.getMessage());
        }
        if(exception instanceof LockedException){                               // 잠긴 계정
            errorCode = ResultCode.LOCKED_ACCOUNT.getCode();
            errorMsg = ResultCode.LOCKED_ACCOUNT.getMessage();
            log.info("### Login Fail : ErrorCode - {}, {}", ResultCode.LOCKED_ACCOUNT.getCode(), ResultCode.LOCKED_ACCOUNT.getMessage());
        }
        if(exception instanceof DisabledException){                             // 계정 비활성화
            errorCode = ResultCode.DISABLED_ACCOUNT.getCode();
            errorMsg = ResultCode.DISABLED_ACCOUNT.getMessage();
            log.info("### Login Fail : ErrorCode - {}, {}", ResultCode.DISABLED_ACCOUNT.getCode(), ResultCode.DISABLED_ACCOUNT.getMessage());
        }
        if(exception instanceof AccountExpiredException){                       // 계정 유효기간 만료
            errorCode = ResultCode.EXPIRED_ACCOUNT.getCode();
            errorMsg = ResultCode.EXPIRED_ACCOUNT.getMessage();
            log.info("### Login Fail : ErrorCode - {}, {}", ResultCode.EXPIRED_ACCOUNT.getCode(), ResultCode.EXPIRED_ACCOUNT.getMessage());
        }
        if(exception instanceof CredentialsExpiredException){                   // 비밀번호 유효기간 만료
            errorCode = ResultCode.EXPIRED_PASSWORD.getCode();
            errorMsg = ResultCode.EXPIRED_PASSWORD.getMessage();
            log.info("### Login Fail : ErrorCode - {}, {}", ResultCode.EXPIRED_PASSWORD.getCode(), ResultCode.EXPIRED_PASSWORD.getMessage());
        }
        if(exception instanceof InternalAuthenticationServiceException){        // 내부오류
            errorCode = ResultCode.INTERNAL_SERVER_ERROR.getCode();
            errorMsg = ResultCode.INTERNAL_SERVER_ERROR.getMessage();
            log.info("### Login Fail : ErrorCode - {}, {}", ResultCode.INTERNAL_SERVER_ERROR.getCode(), ResultCode.INTERNAL_SERVER_ERROR.getMessage());
        }

        ResponseDto<Object> responseDto = ResponseDto.resultSet(errorCode, errorMsg);
        response.setStatus(httpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter out = response.getWriter();
        new ObjectMapper().writeValue(out, responseDto);
        out.flush();
        out.close();
    }
}
