package org.online.lms.video.repository;

import org.online.lms.video.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoInfoRepository extends JpaRepository<Content, Long>{
    @Query("SELECT MAX(c.contentNo) FROM Content c")
    Integer findMaxPosition();

    /*
    //    연결해서 리스트 출력 -- 추후에 추가
    @Query(nativeQuery = true, value = """
    SELECT
        c.content_name as conentName,
        c.content_mgmt_no as contentNo,
        c.content_desc as contentDesc,
        c.content_file_no as contentFileNo,
        c.ytb_url as ytburl,
        c.content_url as contentUrl,
        c.run_tm as rueTm,
        li.lecture_no as lectureNo,
        li.lecture_title as lectureTitle,
        li.lecture_course as lectureCourse
    FROM Content as c
    WHERE c.content_mgmt_no IN
        (SELECT fi.content_mgmt_no FROM Progress_info as fi WHERE fi.lecture_no = :lecture_no)
    """)
    List<VideoInfoViewResponse> findContentByLectureId(@Param("lectureNo") Long lectureNo);
    */



}
