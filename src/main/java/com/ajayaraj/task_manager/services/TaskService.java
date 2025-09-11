package com.ajayaraj.task_manager.services;

import com.ajayaraj.task_manager.dtos.TaskDto;
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
        return taskRepo.save(task);
    }

    public List<Task> getTasks() {
        String userName  = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = authRepo.findByUserName(userName);

        List<Task> tasks = taskRepo.findByUserId(user.getId());

        return tasks;
    }

    public Task getTaskByTaskId(UUID taskId) {
        return taskRepo.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found!"));
    }

    public boolean isCurrentUserIsTheOwnerOfTask(UUID taskId) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = authRepo.findByUserName(userName);

        Task task = taskRepo.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found!"));

        UUID userId = user.getId();
        UUID taskUserId = task.getUserId();

        return userId.equals(taskUserId);
    }

}
