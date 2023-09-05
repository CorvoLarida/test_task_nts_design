package kz.am.fornts.test_task.repository;

import kz.am.fornts.test_task.domain.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {
    List<UserLog> findByUserName(String user);
}
