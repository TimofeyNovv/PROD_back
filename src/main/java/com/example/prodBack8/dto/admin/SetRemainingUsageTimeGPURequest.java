package com.example.prodBack8.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для установки времени использования gpu")
public class SetRemainingUsageTimeGPURequest {

    private String username;
    private Integer timeMinutes;

}
