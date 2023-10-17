package org.online.lms.video.service;


import lombok.extern.slf4j.Slf4j;
import org.online.lms.video.domain.Content;
import org.online.lms.video.dto.VideoInfoRequest;
import org.online.lms.video.repository.VideoInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VideoInfoService {

    private final VideoInfoRepository videoInfoRepository;
    @Autowired
    public VideoInfoService(VideoInfoRepository videoInfoRepository) {
        this.videoInfoRepository = videoInfoRepository;
    }

    // 콘텐츠 등록
    public Content contentRegister(VideoInfoRequest request) {
        Content content = request.toEntity();
        return videoInfoRepository.save(content);
    }

    // 콘텐츠 조회
    public List<Content> findAll() {
        return videoInfoRepository.findAll();
    }
    // 콘텐츠 조회 (페이지네이션)
    public Page<Content> findAll(Pageable pageable) {
        Pageable Pageable =
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by(Sort.Direction.ASC, "contentNo"));
        return videoInfoRepository.findAll(Pageable);
    }

    // 콘텐츠 삭제
    public void delete(Long contentNo) {
        videoInfoRepository.deleteById(contentNo);
    }

//    연결해서 리스트 출력 -- 추후에 추가
//    public List<VideoInfoViewResponse> contentAndLectureList(Long contentNo) {
//        return videoInfoRepository.findContentByLectureId(contentNo);
//    }

    // 유튜브 api에서 추출한 값 Content DB테이블의 컬럼에 저장하기
    public Content saveContent(Content content) {
        return videoInfoRepository.save(content);
    }



}
