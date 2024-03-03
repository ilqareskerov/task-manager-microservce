package com.ilqar.taskservice.service.impl;

import com.ilqar.taskservice.model.Task;
import com.ilqar.taskservice.model.TaskStatus;
import com.ilqar.taskservice.repo.TaskRepository;
import com.ilqar.taskservice.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(Task task, String requesterRole) throws Exception {
        if (!requesterRole.equals("ROLE_ADMIN")){
            throw new Exception("Only admin can create task");
        }
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"+id));
    }

    @Override
    public List<Task> getAllTasks(TaskStatus status) {
        List<Task> tasks = taskRepository.findAll();
        List<Task> filteredTasks = tasks.stream().filter(task -> status ==null || task.getStatus().name().equalsIgnoreCase(status.toString())).collect(Collectors.toList());
        return filteredTasks;
    }

    @Override
    public Task updateTask(Long id, Task updatedTask, Long userId) throws Exception {
        Task existingTask = getTaskById(id);
        if (updatedTask.getTitle()!=null){
            existingTask.setTitle(updatedTask.getTitle());
        }
        if (updatedTask.getDescription()!=null){
            existingTask.setDescription(updatedTask.getDescription());
        }
        if (updatedTask.getStatus()!=null){
            existingTask.setStatus(updatedTask.getStatus());
        }
        if (updatedTask.getDeadline()!=null){
            existingTask.setDeadline(updatedTask.getDeadline());
        }
        if (updatedTask.getImage()!=null){
            existingTask.setImage(updatedTask.getImage());
        }
        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) throws Exception {
        getTaskById(id);
        taskRepository.deleteById(id);

    }

    @Override
    public Task assignedTaskToUser(Long userId, Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }

    @Override
    public List<Task> assignedUsersToTask(Long userId, TaskStatus taskStatus) throws Exception {
        List<Task> all = taskRepository.findByAssignedUserId(userId);
        List<Task> filteredTasks = all.stream().filter(task -> taskStatus ==null || task.getStatus().name().equalsIgnoreCase(taskStatus.toString())).collect(Collectors.toList());
        return filteredTasks;
    }

    @Override
    public Task completeTask(Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }
}
