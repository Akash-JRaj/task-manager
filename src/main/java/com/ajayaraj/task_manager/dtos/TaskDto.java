package com.ajayaraj.task_manager.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TaskDto {
    private UUID id;
    private String taskName;
    private String description;
    private UUID userId;
}
