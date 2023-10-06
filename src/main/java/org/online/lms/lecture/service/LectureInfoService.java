package org.online.lms.lecture.service;

import lombok.RequiredArgsConstructor;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.dto.AddLectureInfoRequest;
import org.online.lms.lecture.dto.UpdateLectureInfoRequest;
import org.online.lms.lecture.repository.LectureInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LectureInfoService {

    @Autowired
    private final LectureInfoRepository lectureInfoRepository;

    // 강의 목록 조회
    public List<LectureInfo> findAll() {
        return lectureInfoRepository.findAll();
    }


    // 강의 계획서 조회
    public LectureInfo findById(Long lectureNo) {
        return lectureInfoRepository.findById(lectureNo)
                .orElseThrow(() -> new IllegalArgumentException("not found" + lectureNo));
    }

    // 강의 추가 메소드
    public LectureInfo addLecture(AddLectureInfoRequest request) {
        LectureInfo lectureInfo = request.toEntity();
        return lectureInfoRepository.save(lectureInfo);
    }

    // 강의 정보 수정
    public LectureInfo updateLecture(Long lectureNo, UpdateLectureInfoRequest request) {
        // 업데이트 할 필드 설정
        Optional<LectureInfo> optionalExistingLecture = lectureInfoRepository.findById(lectureNo);

        if (optionalExistingLecture.isPresent()) {
            LectureInfo existingLecture = optionalExistingLecture.get();

            LectureInfo updatedLecture = LectureInfo.builder()
                    .lectureNo(existingLecture.getLectureNo()) // 기존 lectureNo 유지
                    .lectureYear(request.getLectureYear())
                    .lectureCourse(request.getLectureCourse())
                    .lectureTitle(request.getLectureTitle())
                    .lectureDesc(request.getLectureDesc())
                    .lectureStartAt(request.getLectureStartAt())
                    .lectureEndAt(request.getLectureEndAt())
                    .build();

            // 변경된 정보를 저장
            return lectureInfoRepository.save(updatedLecture);
        } else {
            // 강의를 찾을 수 없는 경우 처리
            return null;
        }
    }

    // 강의 삭제
    public void deleteLectureByLectureNo(Long lectureNo) {
        lectureInfoRepository.deleteById(lectureNo);
    }
}
