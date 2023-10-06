package org.online.lms.security.repository;

import org.online.lms.security.domain.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Members, Long> {

    // 회원가입 시, Member 엔티티 전부 가져오기
    @SuppressWarnings("unchecked")
    @Modifying
    @Transactional
    Members save(Members member);

    // 아이디로 회원 찾기
    Optional<Members> findByLoginId(String loginId);
}
