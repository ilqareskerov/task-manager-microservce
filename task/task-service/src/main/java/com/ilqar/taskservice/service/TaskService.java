package com.ilqar.taskservice.service;

import com.ilqar.taskservice.model.Task;
import com.ilqar.taskservice.model.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    Task createTask(Task task,String requesterRole) throws Exception;
    Task getTaskById(Long id);
    List<Task> getAllTasks(TaskStatus status);
    Task updateTask(Long id , Task updatedTask,Long userId) throws Exception;
    void deleteTask(Long id) throws Exception;
    Task assignedTaskToUser(Long userId,Long taskId) throws Exception;
    List<Task> assignedUsersToTask(Long userId, TaskStatus taskStatus) throws Exception;
    Task completeTask(Long taskId) throws Exception;

}
