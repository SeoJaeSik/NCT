//package io.nexgrid.bizcoretemplate.domain.access.aop;
//
//import io.nexgrid.bizcoretemplate.domain.access.Access;
//import io.nexgrid.bizcoretemplate.domain.access.repository.AccessRepository;
//import io.nexgrid.bizcoretemplate.domain.member.Member;
//import io.nexgrid.bizcoretemplate.util.DateUtil;
//import io.nexgrid.bizcoretemplate.util.LogUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.MDC;
//import org.springframework.stereotype.Component;
//
//import java.net.Inet6Address;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//
//@Slf4j
//@Aspect
//@Component
//@RequiredArgsConstructor
//public class AccessAspect {
//
//    private final HttpServletRequest request;
//    private final AccessRepository accessRepository;
//
//    // @Controller와 @RestController 어노테이션을 포인트컷으로 지정한다.
//    @Pointcut("@within(org.springframework.stereotype.Controller)")
//    public void controller(){}
//    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
//    public void restController(){}
//
//    @Before("controller() || restController()")
//    public void beforeAPI(JoinPoint joinPoint) throws UnknownHostException {
//        String apiName = joinPoint.getSignature().getName();
//        String seqId = LogUtil.generateSeqId();
//
//        // MDC: 스레드-세이프한 로깅 컨텍스트
//        MDC.put("seqId", seqId);
//        log.info("[{}] ========== {} START ==========", seqId, apiName);
//
//        // HttpServletRequest 객체에서 요청에 대한 정보를 가져온다.
//        String requestUri = request.getRequestURI();
//        String accessor = getAccessorUsername();
//        String accessorIp = request.getRemoteAddr();
//
//        log.info("[{}] path = {}, accessor = {}, accessorIp = {}", seqId, requestUri, accessor, accessorIp);
//
//        // Ipv6인 경우 Ipv4로 변환한다.
//        if (InetAddress.getByName(accessorIp) instanceof Inet6Address) {
//            InetAddress inetAddress = InetAddress.getByName(accessorIp);
//            byte[] ipv4Bytes = inetAddress.getAddress();
//
//            accessorIp = ((ipv4Bytes[0] & 0xFF) + "." +
//                    (ipv4Bytes[1] & 0xFF) + "." +
//                    (ipv4Bytes[2] & 0xFF) + "." +
//                    (ipv4Bytes[3] & 0xFF));
//        }
//
//        Access newAccess = Access.builder()
//                .accessor(accessor)
//                .accessorIp(accessorIp)
//                .accessResource(requestUri)
//                .accessYear(DateUtil.getYear())
//                .accessMonth(DateUtil.getMonth())
//                .accessDay(DateUtil.getDay())
//                .accessHour(DateUtil.getHour())
//                .accessTime(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toString())
//                .build();
//
//        Access access = accessRepository.save(newAccess);
//
//        log.info("[{}] Mongo DB INSERT 완료, 데이터 = {}", seqId, access);
//    }
//
//    @After("controller() || restController()")
//    public void AfterAPI(JoinPoint joinPoint) {
//        String apiName = joinPoint.getSignature().getName();
//        String seqId = MDC.get("seqId");
//
//        log.info("[{}] ========== {} END ==========", seqId, apiName);
//        log.info("");
//
//        MDC.remove("seqId");
//    }
//
//
//    // 요청자의 이메일을 가져온다.
//    private String getAccessorUsername() {
//        HttpSession session = request.getSession();
//
//        if (session.getAttribute("member") == null) {
//            return "null";
//
//        } else {
//            Member member = (Member) session.getAttribute("member");
//            return member.getUsername();
//        }
//    }
//
//}