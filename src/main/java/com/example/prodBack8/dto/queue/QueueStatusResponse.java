package com.example.prodBack8.dto.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueStatusResponse {
    private List<QueueItemDto> queueItems;
    private Integer userPosition;
    private String status;
    private Integer activeUsers;
    private Integer availableGpu;
}