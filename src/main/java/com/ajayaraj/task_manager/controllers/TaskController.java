package com.ajayaraj.task_manager.controllers;

import com.ajayaraj.task_manager.models.Task;
import com.ajayaraj.task_manager.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(task));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTasks());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskByTaskId(@PathVariable UUID taskId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskByTaskId(taskId));
    }

    @PutMapping
    public ResponseEntity<Task> updateTask(@RequestBody Task task) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(task));
    }

    @PutMapping("/{taskId}/complete")
    public ResponseEntity<Task> updateCompleteStatus(@PathVariable UUID taskId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateComplete(taskId));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId) {

        taskService.deleteTaskByTaskId(taskId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
