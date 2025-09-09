package com.ajayaraj.task_manager.services;

import com.ajayaraj.task_manager.models.Task;
import com.ajayaraj.task_manager.repos.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;

    public Task createTask(Task task) {
        return taskRepo.save(task);
    }

    public Task getTaskByTaskId(UUID taskId) {
        return taskRepo.getReferenceById(taskId);
    }

}
