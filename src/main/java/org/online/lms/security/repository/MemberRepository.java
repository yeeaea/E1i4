package org.online.lms.security.repository;

import org.online.lms.security.domain.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Members, Long> {

    // 아이디로 회원 찾기
    Optional<Members> findByLoginId(String loginId);
}
