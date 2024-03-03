package com.task.tasksubmissionservice.repo;

import com.task.tasksubmissionservice.modal.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepo extends JpaRepository<Submission, Long> {
    List<Submission> findByTaskId(Long taskId);
}
