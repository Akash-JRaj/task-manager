package com.ajayaraj.task_manager.services;

import com.ajayaraj.task_manager.exceptions.AccessDeniedException;
import com.ajayaraj.task_manager.exceptions.TaskNotFoundException;
import com.ajayaraj.task_manager.models.Task;
import com.ajayaraj.task_manager.models.User;
import com.ajayaraj.task_manager.repos.AuthRepo;
import com.ajayaraj.task_manager.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private AuthRepo authRepo;

    public Task createTask(Task task) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = authRepo.findByUserName(userName);

        task.setUserId(user.getId());

        return taskRepo.save(task);
    }

    public Task updateTask(Task task) {
        if(!isCurrentUserIsTheOwnerOfTask(task.getId())) {
            throw new AccessDeniedException("Not your task to update!");
        }

        return taskRepo.save(task);
    }

    public List<Task> getTasks() {
        String userName  = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = authRepo.findByUserName(userName);

        return taskRepo.findByUserId(user.getId());
    }

    public Task getTaskByTaskId(UUID taskId) {
        if(!isCurrentUserIsTheOwnerOfTask(taskId)) {
            throw new AccessDeniedException("Not your task!");
        }

        return taskRepo.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException("Task with id : " + taskId + " not found!")
        );
    }

    public void deleteTaskByTaskId(UUID taskId) {
        Task task = taskRepo.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException("Task with id : " + taskId + " not found!")
        );

        if(!isCurrentUserIsTheOwnerOfTask(taskId)) {
            throw new AccessDeniedException("Not your task to delete!");
        }

        taskRepo.delete(task);
    }

    public Task updateComplete(UUID taskId) {
        Task task = taskRepo.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException("Task with id : " + taskId + " not found!")
        );

        if(!isCurrentUserIsTheOwnerOfTask(taskId)) {
            throw new AccessDeniedException("Not your task to delete!");
        }

        task.setCompleted(!task.isCompleted());
        return taskRepo.save(task);
    }

    public List<Task> getTasksByCompletionStatus(Boolean completed) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = authRepo.findByUserName(userName);

        return taskRepo.findByUserIdAndCompletionStatus(currentUser.getId(), completed);
    }

    public boolean isCurrentUserIsTheOwnerOfTask(UUID taskId) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = authRepo.findByUserName(userName);

        Task task = taskRepo.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException("Task with id : " + taskId + " not found!")
        );

        UUID userId = user.getId();
        UUID taskUserId = task.getUserId();

        return userId.equals(taskUserId);
    }

}
