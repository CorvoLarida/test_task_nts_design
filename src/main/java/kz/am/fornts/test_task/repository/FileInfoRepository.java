package kz.am.fornts.test_task.repository;

import kz.am.fornts.test_task.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
    FileInfo findByFileName(String fileName);
}
