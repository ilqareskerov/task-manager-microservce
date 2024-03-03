package com.task.tasksubmissionservice.service.impl;

import com.task.tasksubmissionservice.dto.TaskDto;
import com.task.tasksubmissionservice.modal.Submission;
import com.task.tasksubmissionservice.repo.SubmissionRepo;
import com.task.tasksubmissionservice.service.SubmissionService;
import com.task.tasksubmissionservice.service.TaskService;
import com.task.tasksubmissionservice.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class SubmissionServiceImpl implements SubmissionService {
    private final SubmissionRepo submissionRepo;
    private final UserService userService;
    private final TaskService taskService;

    public SubmissionServiceImpl(SubmissionRepo submissionRepo, UserService userService, TaskService taskService) {
        this.submissionRepo = submissionRepo;
        this.userService = userService;
        this.taskService = taskService;
    }

    @Override
    public Submission submitTask(Long taskId, String githubLink, Long userId,String jwt) throws Exception {
        TaskDto taskDto = taskService.getTaskById(taskId, jwt);
        if(taskDto!=null){
            Submission submission = new Submission();
            submission.setTaskId(taskId);
            submission.setGithubLink(githubLink);
            submission.setUserId(userId);
            submission.setSubmissionTime(LocalDateTime.now());
            return submissionRepo.save(submission);
        }
        throw new Exception("Task not found "+taskId);
    }

    @Override
    public Submission getTaskSubmissionById(Long submissionId) throws Exception {
        return submissionRepo.findById(submissionId).orElseThrow(()->new Exception("Submission not found "+submissionId));
    }

    @Override
    public List<Submission> getAllSubmissions() throws Exception {
        return submissionRepo.findAll();
    }

    @Override
    public List<Submission> getTaskSubmissionsByTaskId(Long taskId) throws Exception {
        return submissionRepo.findByTaskId(taskId);
    }

    @Override
    public Submission acceptDeclineTaskSubmission(Long submissionId, String status) throws Exception {
        Submission submission = getTaskSubmissionById(submissionId);
        submission.setStatus(status);
        if (status.equals("ACCEPTED")) {
            taskService.completeTask(submission.getTaskId());
        }
        return submissionRepo.save(submission);
    }
}
