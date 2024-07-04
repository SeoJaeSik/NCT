package io.nexgrid.bizcoretemplate.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private SecurityContextRepository securityContextRepository;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        // security 해시 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DelegatingSecurityContextRepository delegatingSecurityContextRepository() {
        return new DelegatingSecurityContextRepository(new HttpSessionSecurityContextRepository());
    }

    /**
     * Rest API 네이밍 규칙
     * GET - 조회
     * POST - 생성
     * PUT - 변경 (전체 update)
     * PATCH - 변경 (일부분만 update)
     * DELETE - 삭제
     * 명사 사용(복수형) / 소문자 / 구분자는 하이폰 '-' 사용 / url 마지막 슬래쉬, 파일확장자 미포함 / 동사보다는 최대한 명사를 되도록 사용
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthFailureHandler authFailureHandler, AuthSuccessHandler authSuccessHandler) throws Exception {

        http.securityContext((securityContext) -> securityContext
                .securityContextRepository(delegatingSecurityContextRepository())
                .requireExplicitSave(true)
            );

        // 특정요청에 대한 config (순서에 유의)
        http.authorizeHttpRequests((auth) -> auth  // boot 3.1.x ~ 부터 람다형식 필수
                .requestMatchers("/members", "/members/**").permitAll() // 모든 접근 허용
                .requestMatchers("/admin").hasRole("ROOT")  // ROOT 권한 필요
                .requestMatchers("/my/**").hasAnyRole("ROOT", "NORMAL") // ROOT, NORMAL 권한 필요
                // .anyRequest().authenticated() // 지정한 요청 외의 나머지 모든 요청은 인증된 사람만

//                    스웨거 관련 허용
//                    "/api/v1/auth/**",
//                    "/swagger-ui/**",
//                    "/swagger-resources/**",
//                    "/v3/api-docs/**"
            );

        // 로그인 설정
        http.formLogin((auth) -> auth
                /*
                .loginPage("/members/login") // 로그인페이지 redirection
                .defaultSuccessUrl("/main") // 로그인 성공시
                 */
                        .disable()
//                .loginProcessingUrl("/members/login") // 로그인 요청 process
//                .failureHandler(authFailureHandler)
//                .successHandler(authSuccessHandler)
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .permitAll()


                /*
                username, password 로그인이 정상 -> 서버에서 session ID 생성 -> 클라이언트에게 session ID 응답, 쿠키 스토리지에 저장
                -> 앞으로 클라이언트가 요청 시 session ID를 쿠키에 심어서 서버에 요청 (redis-DB)
                -> 서버는 session ID가 유효한지 판단 -> 유효하면 (인증이 필요한) 페이지로 접근하게 시큐리티가 동작함
                */
            );

        http.logout((auth) -> auth
                        .disable()
//                .logoutUrl("/members/logout")
//                .invalidateHttpSession(true)
//                .permitAll()
//                .logoutSuccessUrl("/")
            );


        // security 자동설정 되어있는 사이트 위변조 방지 설정
        http.csrf((auth) -> auth.disable()); // 개발환경 설정

        // 세션정책 설정
        http.sessionManagement((auth) -> auth
                .sessionFixation().changeSessionId() // 동일한 세션에 대한 ID 변경 (쿠키를 이용한 세션탈취 방어)
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Always - 시큐리티가 항상 생성 / Required - 필요시 생성(default) / Never - 생성하진 않지만 존재하면 사용 / Stateless - 생성 X, 사용 X (ex. JWT)
                .maximumSessions(1) // 최대 세션허용 갯수 (다중로그인)
                .maxSessionsPreventsLogin(true) // 세션 허용갯수 초과시 처리 (true - 새로운세션 차단 / false - 기존세션 삭제)
//                .expiredUrl("/members/login") // TODO 세션 만료시 요청 URL
            );


        return http.build();
    }
}
