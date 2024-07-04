package io.nexgrid.bizcoretemplate.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nexgrid.bizcoretemplate.constant.ResultCode;
import io.nexgrid.bizcoretemplate.dto.ResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *  security 로그인/인증 성공관련 처리 핸들러
 */
@Slf4j
@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private final HttpServletResponse httpServletResponse;

    public AuthSuccessHandler(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Auth from SecurityContextHolder: {}", auth);

        SecurityContextHolder.getContext().setAuthentication(authentication);


        WebAuthenticationDetails clientInfo = (WebAuthenticationDetails) authentication.getDetails();

        if (clientInfo == null) {
            log.warn("WebAuthenticationDetails is null");
        } else {
            log.info("WebAuthenticationDetails: {}", clientInfo);
        }


        List<String> roleNames = new ArrayList<>();
        authentication.getAuthorities().forEach(authority -> {
            roleNames.add(authority.getAuthority());
        });

        log.info("### Login Success - UserID : {} {}, IP : {}, Session ID : {}", authentication.getName(), roleNames, clientInfo.getRemoteAddress(), clientInfo.getSessionId());

        ResponseDto<Object> responseDto = ResponseDto.resultSet(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
        response.setStatus(httpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter out = response.getWriter();
        new ObjectMapper().writeValue(out, responseDto);
        out.flush();
        out.close();
    }
}
