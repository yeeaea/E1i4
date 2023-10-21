package org.online.lms.video.repository;

import org.online.lms.video.domain.Content;
import org.online.lms.video.domain.Progress;
import org.online.lms.video.domain.ProgressInfo;
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


    // '회원번호 + 콘텐츠번호 = 고유번호'를 통해서 finalTm 가져오기
    @Query("""
        SELECT p.finalTm
        FROM Progress p
        WHERE p.contentNo = :contentNo and p.memberNo = :memberNo
        ORDER BY p.progressNo DESC
        LIMIT 1
    """)
    String findFinalTmByContentNoAndMemberNo(@Param("contentNo") Long contentNo, Long memberNo);

    // '회원번호 + 콘텐츠번호 = 고유번호'를 통해서 maxTm 가져오기
    @Query("""
        SELECT p.maxTm
        FROM Progress p
        WHERE p.contentNo = :contentNo and p.memberNo = :memberNo
        ORDER BY p.progressNo DESC
        LIMIT 1
    """)
    String findMaxTmByContentNoAndMemberNo(@Param("contentNo") Long contentNo, Long memberNo);


    // 출석
    // 차시 진도율[b/T*100]
    // ?
    @Query("""
    SELECT p.progRt
    FROM Progress p
    WHERE p.contentNo = :contentNo and p.memberNo = :memberNo
    ORDER BY p.progressNo DESC
    LIMIT 1
""")
    Long findProgRtByContentNoAndMemberNo(@Param("contentNo") Long contentNo, @Param("memberNo") Long memberNo);
//    @Query("""
//    SELECT p.progRt
//    FROM ProgressInfo p
//    LEFT JOIN Content c ON c.content.contentNo = p.contentNo
//    WHERE pi.content.contentNo = :contentNo
//    """)
//    List<Object[]> findResult(@Param("contentNo") Long contentNo);
}