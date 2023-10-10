package org.online.lms.lecture.service;

import lombok.RequiredArgsConstructor;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.dto.AddLectureInfoRequest;
import org.online.lms.lecture.dto.UpdateLectureInfoRequest;
import org.online.lms.lecture.repository.LectureInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LectureInfoService {

    @Autowired
    private final LectureInfoRepository lectureInfoRepository;

    // 강의 목록 조회
    @Transactional(readOnly = true)
    public List<LectureInfo> findAll() {
        return lectureInfoRepository.findAll();
    }

    // 강의 번호로 강의 객체 불러 오기
    @Transactional(readOnly = true)
    public LectureInfo getLectureByLectureNo(Long lectureNo) {
        return lectureInfoRepository.findById(lectureNo).orElse(null);
    }

    // 강의 계획서 조회
    @Transactional(readOnly = true)
    public LectureInfo findById(Long lectureNo) {
        return lectureInfoRepository.findById(lectureNo)
                .orElseThrow(() -> new IllegalArgumentException("not found" + lectureNo));
    }

    // 강의 추가 메소드
    @Transactional
    public LectureInfo addLecture(AddLectureInfoRequest request) {
        LectureInfo lectureInfo = request.toEntity();
        return lectureInfoRepository.save(lectureInfo);
    }

    // 강의 정보 수정
    @Transactional
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
    @Transactional
    public boolean deleteLectures(List<Long> lectureNos) {
        try {
            // lectureNos 목록에 포함된 강의 삭제
            lectureInfoRepository.deleteByLectureNoIn(lectureNos);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
