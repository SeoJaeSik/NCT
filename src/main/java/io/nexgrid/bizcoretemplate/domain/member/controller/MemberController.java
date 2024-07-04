package io.nexgrid.bizcoretemplate.domain.member.controller;

import io.nexgrid.bizcoretemplate.config.security.CustomUserDetailService;
import io.nexgrid.bizcoretemplate.constant.ResultCode;
import io.nexgrid.bizcoretemplate.domain.member.Member;
import io.nexgrid.bizcoretemplate.domain.member.dto.LoginDto;
import io.nexgrid.bizcoretemplate.domain.member.dto.SignUpDto;
import io.nexgrid.bizcoretemplate.domain.member.service.MemberService;
import io.nexgrid.bizcoretemplate.dto.ResponseDto;
import io.nexgrid.bizcoretemplate.handler.exception.ApiParameterNotValidException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final CustomUserDetailService customUserDetailService;

    /*
        ※ Spring Validation
        boot 3.1 버전 이상부터는 유연한 에러처리를 위해 @Valid에 대한 에외를 발생시키지 않음 -> Custom으로 처리
    */

    /*
        TODO 기능 추가 List
        - ID 중복 체크
        - 멤버 정보 변경
        - 로그인, 로그아웃 (세션 - redis)
        - 요청, 응답에 대한 로그
     */

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<Object>> signUpRequest(@Validated @RequestBody SignUpDto signUpDTO,
                                                             BindingResult bindingResult) throws Exception {
        // Validation DTO에 명시 (Validation 실패시 예외 핸들러가 처리)
        log.info("▷▷▷ SignUp Request : {}", signUpDTO.toString());

        // 파라미터 Validation 체크
        if (bindingResult.hasErrors()) {
             throw new ApiParameterNotValidException(bindingResult);
        }

        try {
            memberService.signUpProcess(signUpDTO);
        } catch (DataIntegrityViolationException ex) {
            log.info("### DUPLICATE_ID : {} - 이미 존재하는 계정으로 가입요청", signUpDTO.getUsername());
            return ResponseEntity.badRequest().body(ResponseDto.resultSet(ResultCode.DUPLICATE_ID.getCode()
                                                                        , ResultCode.DUPLICATE_ID.getMessage()));
        }

        return ResponseEntity.ok(ResponseDto.resultSet(ResultCode.SUCCESS.getCode()
                                                     , ResultCode.SUCCESS.getMessage()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Object>> getUserData(@Validated @Min(1) @PathVariable("id") Long id) throws Exception {

        log.info("▷▷▷ Get UserData Request - UserID Key: {}", id);
        Member member = memberService.getUserById(id);

        if (member == null) {
            log.info("### Member Not Found - 조회된 정보가 없습니다. UserID Key : {}", id);
            return ResponseEntity.ok(ResponseDto.resultSet(ResultCode.NOT_FOUND_DATA.getCode()
                                                         , ResultCode.NOT_FOUND_DATA.getMessage()));
        }

        return ResponseEntity.ok(ResponseDto.resultSet(ResultCode.SUCCESS.getCode()
                                                     , ResultCode.SUCCESS.getMessage()
                                                     , member));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Object>> deleteUserData(@Validated @Min(1) @PathVariable("id") Long id) throws Exception {

        log.info("▷▷▷ Delete UserData Request - UserID Key: {}", id);

        boolean result = memberService.deleteById(id);

        if (!result) {
            log.info("### Member Not Found - 존재하지 않는 사용자입니다. UserID Key : {}", id);
            return ResponseEntity.ok(ResponseDto.resultSet(ResultCode.MEMBER_NOT_FOUND.getCode()
                                                         , ResultCode.MEMBER_NOT_FOUND.getMessage()));
        }

        return ResponseEntity.ok(ResponseDto.resultSet(ResultCode.SUCCESS.getCode()
                                                     , ResultCode.SUCCESS.getMessage()));
    }


    @GetMapping("/info")
    public String getMemberInfo(Model model, HttpSession session) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        // 세션정보(ID, Role) 가져오기

        log.info("username : {}", username);
        log.info("role : {}", role);
        model.addAttribute("username", username);
        model.addAttribute("role", role);

        String sessionId = session.getId();
        log.info("sessionId : {}", sessionId);

        return "info";
    }


    /*
        1. 사용자가 로그인 요청을 서버에 보냄
        2. 서버는 로그인 요청을 받아 인증을 수행
        3. 인증되면 세션 정보를 생성 후 레디스에 저장 후 세션 ID 클라이언트에게 전송
        4. 레디스 서버는 세션 데이터를 저장, 세션 ID를 키로 사용하여 세션 데이터를 저장하고 조회
        5. 클라이언트는 세션 ID를 쿠키를 통해 저장
        6. 사용자의 후속 요청이 올 때마마다 세션 식별자를 확인
        7. 세션의 만료 시간을 설정하고 일정 기간 활동이 없으면 세션은 자동으로 만료되지만, 사용자의 활동이 있을 때마다 세션의 만료 시간을 갱신
    */

    @PostMapping("/login")
    public String login (@RequestBody LoginDto loginDto, HttpSession session) {

        log.info("▷▷▷ Login Request - UserID : {}", loginDto.getUsername());

        UserDetails userDetails = customUserDetailService.loadUserByUsername(loginDto.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        session.setMaxInactiveInterval(3600);

        log.info("userDetails : {}", userDetails);
        log.info("authentication : {}", authentication);

        return "login";
    }

    @GetMapping("/logout")
    public void logout (HttpSession session) {
        session.invalidate();
    }


}



