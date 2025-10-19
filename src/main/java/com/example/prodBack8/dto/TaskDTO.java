package com.example.prodBack8.dto;

import com.example.prodBack8.model.entity.history.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TaskDTO {
    private Long id;
    private Integer countGPU;
    private UserDTO user;
    private GroupDTO group;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer usageDuration;

    private TaskStatus status;
}