package org.online.lms.security.service;

import org.online.lms.security.domain.Members;
import org.online.lms.security.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    // 관리자 페이지에서 사용자 목록 출력
    public List<Members> findAll(){
        return adminRepository.findAll();
    }

    // 관리자 페이지에서 수강생 목록 출력
    public List<Members> findStudentList(Long lectureNo){
        return adminRepository.findMembersByLectureNo(lectureNo);
    }

}
