package io.nexgrid.bizcoretemplate.config.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.nexgrid.bizcoretemplate.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class CustomUserDetails implements UserDetails {

    private Member member;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Member member) {
        this.member = member;
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole()));
    }

    /**
     * 사용자에게 부여된 권한 목록을 반환
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    /**
     * 사용자의 암호화된 비밀번호 반환
     */
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    /**
     * 사용자의 ID 반환
     */
    @Override
    public String getUsername() {
        return member.getUsername();
    }

    /**
     * 사용자의 계정이 만료되었는지의 여부
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 사용자의 계정이 잠겨있는지 여부
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 사용자의 자격증명(비밀번호)이 만료되었는지 여부
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 사용자의 계정이 활성화되었는지 여부
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
