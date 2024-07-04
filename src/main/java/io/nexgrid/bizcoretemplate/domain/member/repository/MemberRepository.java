package io.nexgrid.bizcoretemplate.domain.member.repository;

import io.nexgrid.bizcoretemplate.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    // Member field에 존재하는 값으로 메소드 작명

    Member findByUsername(String username);

    Optional<Member> findById(Long id);

//    에러처리 예시
//    Member member = memberRepository.findByRefreshToken(token)
//            .orElseThrow(() -> new MemberException(ErrorCode.INVALID_TOKEN));


}
