package com.example.prodBack8.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для установки ограничений группы")
public class UpdateGroupLimitsRequest {
    @Min(1)
    @Max(1440) // 24 часа в минутах
    private Integer maxSessionDurationMinutes;


    // Или для раздельного хранения:
    private String allowedDays;
    private LocalTime dayStartTime;
    private LocalTime dayEndTime;
}
