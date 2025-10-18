package com.example.prodBack8.dto.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueItemDto {
    private Long id;
    private Long userId;
    private String userName;
    private Long groupId;
    private String groupName;
    private Integer position;
    private LocalDateTime joinedAt;
    private String status;
    private String waitingTime; // в человеко-читаемом формате
}
