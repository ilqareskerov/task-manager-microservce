package com.ilqar.taskservice.controller;

import com.ilqar.taskservice.controller.feign.UserService;
import com.ilqar.taskservice.dto.UserDto;
import com.ilqar.taskservice.model.Task;
import com.ilqar.taskservice.model.TaskStatus;
import com.ilqar.taskservice.service.impl.TaskServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskServiceImpl taskService;
    private final UserService userService;

    public TaskController(TaskServiceImpl taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }
@PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestHeader("Authorization") String token) throws Exception{
        Task createdTask = taskService.createTask(task,userService.getUserProfile(token).getRole());
        return new ResponseEntity<>(createdTask , HttpStatus.CREATED);
    }
@GetMapping("/user")
    public ResponseEntity<List<Task>> getAssignedUsersTask(@RequestParam(required = false) TaskStatus status,
                                                     @RequestHeader("Authorization") String token) throws Exception {
        List<Task> task = taskService.assignedUsersToTask(userService.getUserProfile(token).getId(),status);
        return new ResponseEntity<>(task , HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<List<Task>> getAllTask(@RequestParam(required = false) TaskStatus status,
                                                           @RequestHeader("Authorization") String token) throws Exception {
        List<Task> task = taskService.getAllTasks(status);
        return new ResponseEntity<>(task , HttpStatus.OK);
    }
    @PutMapping("/{id}/user/{userId}/assigned")
    public ResponseEntity<Task> getAssignedTaskToUser(@PathVariable Long id,
                                                 @PathVariable Long userId,
                                                 @RequestHeader("Authorization") String token) throws Exception {
        Task task = taskService.assignedTaskToUser(userId,id);
        return new ResponseEntity<>(task , HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,@RequestBody Task task,
                                           @RequestHeader("Authorization") String token) throws Exception {
        Task tasks = taskService.updateTask(id,task,userService.getUserProfile(token).getId());
        return new ResponseEntity<>(tasks , HttpStatus.OK);
    }
    @PutMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable Long id) throws Exception {
        Task tasks = taskService.completeTask(id);
        return new ResponseEntity<>(tasks , HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) throws Exception {
        taskService.deleteTask(id);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id,
                                                 @RequestHeader("Authorization") String token) throws Exception {
        UserDto user = userService.getUserProfile(token);
        Task task = taskService.getTaskById(id);
        return new ResponseEntity<>(task , HttpStatus.OK);
    }
}
