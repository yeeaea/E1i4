package org.online.lms.lecture.service;

import org.online.lms.lecture.domain.LectureApply;
import org.online.lms.lecture.domain.LectureInfo;
import org.online.lms.lecture.dto.AddLectureInfoRequest;
import org.online.lms.lecture.dto.UpdateLectureInfoRequest;
import org.online.lms.lecture.repository.LectureApplyRepository;
import org.online.lms.lecture.repository.LectureInfoRepository;
import org.online.lms.survey.repository.SurveyAnsRepository;
import org.online.lms.survey.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LectureInfoService {

    private final LectureInfoRepository lectureInfoRepository;
    private final LectureApplyRepository lectureApplyRepository;
    private final SurveyAnsRepository surveyAnsRepository;
    private final SurveyRepository surveyRepository;

    @Autowired
    public LectureInfoService(LectureInfoRepository lectureInfoRepository,
                              LectureApplyRepository lectureApplyRepository,
                              SurveyAnsRepository surveyAnsRepository,
                              SurveyRepository surveyRepository) {
        this.lectureInfoRepository = lectureInfoRepository;
        this.lectureApplyRepository = lectureApplyRepository;
        this.surveyAnsRepository = surveyAnsRepository;
        this.surveyRepository = surveyRepository;
    }

    // 강의 목록 조회
    @Transactional(readOnly = true)
    public List<LectureInfo> findAll() {
        return lectureInfoRepository.findAll();
    }

    public Page<LectureInfo> findAll(Pageable pageable) {
        Pageable Pageable =
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by(Sort.Direction.DESC, "lectureNo"));

        return lectureInfoRepository.findAll(Pageable);
    }


    // 강의 번호로 강의 정보 얻기
    public LectureInfo getLectureInfoByLectureNo(Long lectureNo) {
        // 강의 번호를 이용하여 해당 강의 정보를 조회
        Optional<LectureInfo> optionalLectureInfo = lectureInfoRepository.findById(lectureNo);

        if (optionalLectureInfo.isPresent()) {
            return optionalLectureInfo.get();
        } else {
            // 해당 강의 번호에 해당하는 강의 정보가 없을 경우 예외 던지기
            throw new LectureNotFoundException("강의 번호 " + lectureNo + "에 해당하는 강의 정보를 찾을 수 없습니다.");
        }
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
    public LectureInfo updateLectures(Long lectureNo,
                                      UpdateLectureInfoRequest request) {
        LectureInfo lectureInfo = lectureInfoRepository.findById(lectureNo).orElseThrow(
                () -> new IllegalArgumentException(lectureNo + "번 강의가 존재하지 않습니다."));

        lectureInfo.update(request.getLectureYear(),
                request.getLectureTitle(),
                request.getLectureDesc(),
                request.getLectureStartAt(),
                request.getLectureEndAt(),
                request.getLectureCourse(),
                request.getLectureDuration());

        return lectureInfo;
    }

    // 강의 삭제
    @Transactional
    public boolean deleteLectures(List<Long> lectureNos) {
        try {
            // 각각의 강의 번호에 대해 수강 신청 데이터를 찾아 삭제
            // 강의 번호로 수강신청 데이터 찾기
            List<LectureApply> lectureApplies = lectureApplyRepository.findByLectureNoIn(lectureNos);

            // 수강신청 데이터 삭제
            lectureApplyRepository.deleteAll(lectureApplies);

            // 강의평가 답변항목 데이터 삭제
            surveyAnsRepository.deleteByLectureNoIn(lectureNos);

            // 강의평가 데이터 삭제
            surveyRepository.deleteByLectureNoIn(lectureNos);

            // lectureNos 목록에 포함된 강의 삭제
            lectureInfoRepository.deleteByLectureNoIn(lectureNos);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // 예외 클래스
    public class LectureNotFoundException extends RuntimeException {
        public LectureNotFoundException(String message) {
            super(message);
        }
    }

}
