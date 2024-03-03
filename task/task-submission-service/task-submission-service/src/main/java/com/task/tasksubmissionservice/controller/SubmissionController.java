package com.task.tasksubmissionservice.controller;

import com.task.tasksubmissionservice.dto.UserDto;
import com.task.tasksubmissionservice.modal.Submission;
import com.task.tasksubmissionservice.service.SubmissionService;
import com.task.tasksubmissionservice.service.TaskService;
import com.task.tasksubmissionservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/submissions")
public class SubmissionController {
    private final SubmissionService submissionService;
    private final TaskService taskService;
    private final UserService userService;

    public SubmissionController(SubmissionService submissionService, TaskService taskService, UserService userService) {
        this.submissionService = submissionService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Submission> submitTask(@RequestParam Long taskId, @RequestParam String githubLink,@RequestHeader("Authorization") String jwt) throws Exception {
        UserDto userDto = userService.getUserProfile(jwt);
        return ResponseEntity.ok(submissionService.submitTask(taskId,githubLink,userDto.getId(), jwt));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> submitTask(@PathVariable Long id,@RequestHeader("Authorization") String jwt) throws Exception {
        UserDto userDto = userService.getUserProfile(jwt);
        return ResponseEntity.ok(submissionService.getTaskSubmissionById(id));
    }
    @GetMapping("/task/{id}")
    public ResponseEntity<List<Submission>> getAllSubmissions(@PathVariable Long id,@RequestHeader("Authorization") String jwt) throws Exception {
        UserDto userDto = userService.getUserProfile(jwt);
        return ResponseEntity.ok(submissionService.getTaskSubmissionsByTaskId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Submission> acceptDeclineTaskSubmission(@PathVariable Long id,@RequestParam("status") String status ,@RequestHeader("Authorization") String jwt) throws Exception {
        UserDto userDto = userService.getUserProfile(jwt);
        Submission submissions = submissionService.acceptDeclineTaskSubmission(id,status);
        return ResponseEntity.ok(submissions);
    }
}
