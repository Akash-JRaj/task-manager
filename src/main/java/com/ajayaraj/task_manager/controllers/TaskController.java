package com.ajayaraj.task_manager.controllers;

import com.ajayaraj.task_manager.models.Task;
import com.ajayaraj.task_manager.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<Page<Task>> getTasks(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "taskName") String sortBy,
                                               @RequestParam(defaultValue = "true") boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTasks(pageable));
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

    @GetMapping("/search")
    public ResponseEntity<List<Task>> filterByCompletionStatus(@RequestParam(value = "status", required = false) Boolean status) {
        if(status != null) {
            return ResponseEntity.status(HttpStatus.OK).body(taskService.getTasksByCompletionStatus(status));
        }

        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasks());
    }

}
