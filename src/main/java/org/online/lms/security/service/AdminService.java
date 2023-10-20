package org.online.lms.security.service;

import org.online.lms.security.domain.Members;
import org.online.lms.security.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // 관리자 페이지에서 사용자 목록 출력
    public Page<Members> findAll(Pageable pageable) {

        Pageable Pageable =
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by(Sort.Direction.DESC, "memberNo"));

        return adminRepository.findAll(pageable);
    }

    // 관리자 페이지에서 수강생 목록 출력
    public Page<Members> findStudentList(Long lectureNo,Pageable pageable) {
        return adminRepository.findMembersByLectureNo(lectureNo,pageable);
    }

}
