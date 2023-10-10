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

    // 아이디 찾기 : 사용자 이름 및 이메일 입력받아 아이디 조회
    Optional<Members> findByMemberNameAndMemberEmail(String memberName, String memberEmail);

    // 비밀번호 찾기 : 사용자 이메일 주소로 사용자 조회
    Optional<Members> findByMemberEmail(String memberEmail);
}
