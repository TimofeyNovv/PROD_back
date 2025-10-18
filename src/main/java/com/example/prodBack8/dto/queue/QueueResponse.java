package com.example.prodBack8.dto.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueResponse {
    private String message;
    private Integer position;
    private Integer totalInQueue;
    private Long estimatedWaitTime; // в минутах
}
