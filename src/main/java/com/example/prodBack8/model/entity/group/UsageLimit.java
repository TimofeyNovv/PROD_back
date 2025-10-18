package com.example.prodBack8.model.entity.group;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalTime;

@Embeddable
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsageLimit {
    // Максимальное время выполнения задачи в минутах
    private Integer maxSessionDurationMinutes;

    private String allowedDays; // "MON,TUE,WED,THU,FRI"
    private String dayStartTime;
    private String dayEndTime;
}