package io.nexgrid.bizcoretemplate.config.security;

import io.nexgrid.bizcoretemplate.domain.member.Member;
import io.nexgrid.bizcoretemplate.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("▷▷▷ Security Login Request - UserID : {}", username);
        Member member = memberRepository.findByUsername(username);

        // TODO 비밀번호 틀렸을 경우 실패카운트, 잠김처리 구현

        if (member == null) {
            log.info("### Login Fail - Could not found User from DB (by UserID : {})", username);
            throw new UsernameNotFoundException("Could not found User");
        } else {
            log.info("### Search Success - UserID : {} / Member Info : {}", username, member.toString());
        }

        return new CustomUserDetails(member);
    }
}
