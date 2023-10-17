package org.online.lms.video.repository;

import lombok.extern.slf4j.Slf4j;
import org.online.lms.video.domain.Progress;
import org.online.lms.video.domain.ProgressInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProgressTmRepository extends JpaRepository<Progress, Long> {
    Progress findByProgressNo(Long progressNo);

    // 차시 번호 통해 콘텐츠 테이블의 유튜브 연동번호 받아오기
    // (progress_info와 content를 조인해서 유튜브 연동번호 가져오고,
    // progress 테이블의 nthNo = progress_info테이블의 nthNo 정보를 가져오면 됨)
//    @Query(value = " SELECT p.nth_no\n" +
//            "FROM progress p\n" +
//            "INNER JOIN (\n" +
//            "    SELECT pi.nth_no\n" +
//            "    FROM progress_info pi\n" +
//            "    JOIN content c ON pi.content_mgmt_no = c.content_mgmt_no\n" +
//            "    WHERE c.ytbUrl = '원하는_ytbUrl_값'\n" +
//            ") pp\n" +
//            "ON pp.nth_no = p.nth_no")
//    List<Long> findProgressBynthNo(Long nthNo);

    /*
    참고
    // 3. 게시물 하나에 작성된 해시태그를 조회
	@Query(value = "SELECT t.tag_content "
		+ "	   FROM tag t"
		+ "    INNER JOIN ("
		+ "			SELECT bt.tag_id"
		+ "				FROM board_tag bt"
		+ "				JOIN board b"
		+ "        			ON bt.board_id = b.board_id"
		+ "				WHERE b.board_id = :#{#boardId}"
		+ "			) bb"
		+ "		ON bb.tag_id = t.tag_id"
		, nativeQuery = true)
	// boardId를 파라미터로 받아 해당 게시물에 해당하는 모든 해시태그를 반환
	List<String> findTagsByBoardId(@Param("boardId") Long boardId);
     */
}