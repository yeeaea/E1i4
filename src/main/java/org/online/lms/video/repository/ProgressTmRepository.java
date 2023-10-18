package org.online.lms.video.repository;

import org.online.lms.video.domain.Content;
import org.online.lms.video.domain.Progress;
import org.online.lms.video.domain.ProgressInfo;
import org.online.lms.video.dto.ProgressTmRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressTmRepository extends JpaRepository<Progress, Long> {
    Optional<Progress> findByProgressNo(Long progressNo);

    // nthNo 번호 가져오기 progress와 lec
//    @Query("SELECT p.nthNo FROM Progress p JOIN p.lectureNo l WHERE l.lectureNo = :lectureNo")
//    List<Long> findNthNoByLectureNo(@Param("lectureNo") String lectureNo);

    // 차시 전체를 가져오기 -> 재생목록 출력 시 사용
    // 강의 1개 : 차시 1,2,3 ...
    @Query("""
        SELECT pi
        FROM ProgressInfo pi
        WHERE pi.lecture.lectureNo = :lectureNo 
    """)
    List<Long> findByLectureNo(@Param("lectureNo") Long lectureNo);




    // 강의번호 통해 강의번호에 연결된 content 전체 가져오기 - 사용
    @Query("""
        SELECT pi.content
        FROM ProgressInfo pi
        WHERE pi.lecture.lectureNo = :lectureNo
    """)
    List<Content> findContentByLectureNo(@Param("lectureNo") Long lectureNo);


    // 콘텐츠 번호 통해 content 전체 가져오기
    @Query("""
        SELECT pi.content
        FROM ProgressInfo pi
        WHERE pi.content.contentNo = :contentNo
    """)
    List<Content> findContentByContentNo(@Param("contentNo") Long contentNo);


    // 콘텐츠 번호 통해 ProgressInfo 전체 가져오기
    @Query("""
        SELECT pi
        FROM ProgressInfo pi
        WHERE pi.content.contentNo = :contentNo
    """)
    List<ProgressInfo> findProgressInfosByContentNo(@Param("contentNo") Long contentNo);


    // 콘텐츠 번호 통해 Progress 전체 가져오기 (필요하면 나중에 progressNo하나만)
    // 아니면 반환값 List<Progress> findProgressByContentNo
    @Query("""
        SELECT p
        FROM Progress p
        WHERE p.nthNo.content.contentNo = :contentNo
    """)
    List<ProgressTmRequest> findProgressTmRequestByContentNo(@Param("contentNo") Long contentNo);


    // 로그인 아이디가 있으면 중복 저장하는 업데이트


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