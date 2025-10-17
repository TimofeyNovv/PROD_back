package com.example.prodBack8.model.entity.group;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Embeddable
@Setter
@Getter
public class UsageLimit {
    // Максимальное время выполнения задачи в минутах
    private Integer maxSessionDurationMinutes;

    private String allowedDays; // "MON,TUE,WED,THU,FRI"
    private LocalTime dayStartTime;
    private LocalTime dayEndTime;
}