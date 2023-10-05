package org.online.lms.security.repository;

import org.online.lms.security.domain.Members;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepoistory extends JpaRepository<Members,Long> {
}
