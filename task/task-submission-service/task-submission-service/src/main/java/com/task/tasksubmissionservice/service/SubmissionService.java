package com.task.tasksubmissionservice.service;

import com.task.tasksubmissionservice.modal.Submission;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SubmissionService {
    Submission submitTask(Long taskId, String githubLink, Long userId,String jwt) throws Exception;
    Submission getTaskSubmissionById(Long submissionId) throws Exception;
    List<Submission> getAllSubmissions() throws Exception;
    List<Submission> getTaskSubmissionsByTaskId(Long taskId) throws Exception;
    Submission acceptDeclineTaskSubmission(Long submissionId, String status) throws Exception;
}
