package com.example.prodBack8.dto.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueueRequest {
    private Long userId;
    private Long groupId;
}
