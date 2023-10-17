package org.online.lms.video.service;

import lombok.extern.slf4j.Slf4j;
import org.online.lms.video.domain.Progress;
import org.online.lms.video.dto.ProgressTmRequest;
import org.online.lms.video.repository.ProgressTmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static groovyjarjarantlr4.v4.gui.Trees.save;
@Slf4j
@Service
public class ProgressTmService {

    private final ProgressTmRepository progressTmRepository;

    @Autowired
    public ProgressTmService(ProgressTmRepository progressTmRepository) {
        this.progressTmRepository = progressTmRepository;
    }

    public Progress saveTmData(Progress progress) {
        return progressTmRepository.save(progress);
    }

    public Progress findByProgressNo(Long progressNo) {
        return progressTmRepository.findByProgressNo(progressNo);
    }

    public void save(ProgressTmRequest req) {
    }
}
