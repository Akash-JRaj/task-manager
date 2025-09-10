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

    public List<Task> getTasks() {
        String userName  = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = authRepo.findByUserName(userName);

        List<Task> tasks = taskRepo.findByUserId(user.getId());

        return tasks;
    }

    public TaskDto getTaskByTaskId(UUID taskId) {
        Task task = taskRepo.getReferenceById(taskId);
        return new TaskDto(task.getId(), task.getTaskName(), task.getDescription(), task.getUserId());
    }

    public boolean isCurrentUserIsTheOwnerOfTask(UUID taskId) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = authRepo.findByUserName(userName);

        Task task = taskRepo.getReferenceById(taskId);

        UUID userId = user.getId();
        UUID taskUserId = task.getUserId();

        return userId.equals(taskUserId);
    }

}
