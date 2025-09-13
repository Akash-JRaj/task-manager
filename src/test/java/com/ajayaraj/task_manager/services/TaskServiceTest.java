package com.ajayaraj.task_manager.services;

import com.ajayaraj.task_manager.models.Task;
import com.ajayaraj.task_manager.models.User;
import com.ajayaraj.task_manager.repos.AuthRepo;
import com.ajayaraj.task_manager.repos.TaskRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @MockitoBean
    private TaskRepo taskRepo;

    @MockitoBean
    private AuthRepo authRepo;

    private void mockAuthenticatedUser(String userName) {
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn(userName);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testCreateTask() {
        mockAuthenticatedUser("testUser");

        User mockUser = new User();
        mockUser.setId(UUID.randomUUID());
        mockUser.setUserName("testUser");

        Mockito.when(authRepo.findByUserName(Mockito.any())).thenReturn(mockUser);

        Task task = new Task();
        task.setTaskName("TaskTest");

        Mockito.when(taskRepo.save(Mockito.any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task);
        assertNotNull(createdTask);
        assertEquals("TaskTest", createdTask.getTaskName());
    }

    @Test
    void testUpdateTask() {
        mockAuthenticatedUser("testUser");

        User mockUser = new User();
        mockUser.setId(UUID.randomUUID());
        mockUser.setUserName("testUser");

        Mockito.when(authRepo.findByUserName(Mockito.any())).thenReturn(mockUser);

        Task existingTask = new Task();
        existingTask.setId(UUID.randomUUID());
        existingTask.setTaskName("original");
        existingTask.setUserId(mockUser.getId());

        Mockito.when(taskRepo.save(Mockito.any(Task.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        Mockito.when(taskRepo.findById(Mockito.any())).thenReturn(Optional.of(existingTask));

        existingTask.setTaskName("updated");

        Task updatedTask = taskService.updateTask(existingTask);

        assertNotNull(updatedTask);
        assertEquals("updated", updatedTask.getTaskName());
    }

    @Test
    void testGetTaskByTaskId() {
        mockAuthenticatedUser("testUser");

        User mockUser = new User();
        mockUser.setId(UUID.randomUUID());
        mockUser.setUserName("testUser");

        Mockito.when(authRepo.findByUserName(Mockito.any())).thenReturn(mockUser);

        Task task = new Task();
        task.setId(UUID.randomUUID());
        task.setTaskName("task1");
        task.setUserId(mockUser.getId());

        Mockito.when(taskRepo.findById(task.getId())).thenReturn(Optional.of(task));

        Task task1 = taskService.getTaskByTaskId(task.getId());
        assertNotNull(task1);
        assertEquals(task.getTaskName(), task1.getTaskName());
    }

    @Test
    void testGetTasks() {
        mockAuthenticatedUser("testUser");

        User mockUser = new User();
        mockUser.setId(UUID.randomUUID());
        mockUser.setUserName("testUser");

        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task();
        task1.setTaskName("task1");
        task1.setUserId(mockUser.getId());

        Task task2 = new Task();
        task2.setTaskName("task2");
        task2.setUserId(mockUser.getId());

        tasks.add(task1);
        tasks.add(task2);

        Mockito.when(authRepo.findByUserName("testUser")).thenReturn(mockUser);
        Mockito.when(taskRepo.findByUserId(mockUser.getId())).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();
        assertNotNull(result);
        assertEquals(tasks.size(), result.size());
    }
}
