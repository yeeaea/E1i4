package org.online.lms.boards.repository;

import org.online.lms.boards.domain.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {

}
