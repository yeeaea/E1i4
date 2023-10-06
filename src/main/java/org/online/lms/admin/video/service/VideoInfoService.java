package org.online.lms.admin.video.service;

import org.online.lms.video.domain.Content;
import org.online.lms.admin.video.dto.VideoInfoRequest;
import org.online.lms.admin.video.repository.VideoInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Content> findAll() { return videoInfoRepository.findAll(); }

//    연결해서 리스트 출력 -- 추후에 추가
//    public List<VideoInfoViewResponse> contentAndLectureList(Long contentNo) {
//        return videoInfoRepository.findContentByLectureId(contentNo);
//    }

}
