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

    public boolean isWithinAllowedTime() {
        // Парсим время начала
        String[] startParts = dayStartTime.split(":");
        int startHour = Integer.parseInt(startParts[0]);
        int startMinute = Integer.parseInt(startParts[1]);

        // Парсим время окончания
        String[] endParts = dayEndTime.split(":");
        int endHour = Integer.parseInt(endParts[0]);
        int endMinute = Integer.parseInt(endParts[1]);

        // Получаем текущее время
        java.time.LocalTime now = java.time.LocalTime.now();
        int currentHour = now.getHour();
        int currentMinute = now.getMinute();

        // Преобразуем все в минуты для простого сравнения
        int startTotalMinutes = startHour * 60 + startMinute;
        int endTotalMinutes = endHour * 60 + endMinute;
        int currentTotalMinutes = currentHour * 60 + currentMinute;

        // Сравниваем
        return currentTotalMinutes >= startTotalMinutes && currentTotalMinutes <= endTotalMinutes;
    }

    public boolean isWithinAllowedDays() {
        if (allowedDays == null || allowedDays.trim().isEmpty()) {
            return true; // Если дни не указаны, доступ всегда разрешен
        }

        // Получаем текущий день недели в коротком формате (MON, TUE, etc.)
        String currentDay = java.time.DayOfWeek.from(java.time.LocalDate.now())
                .name()
                .substring(0, 3)
                .toUpperCase();

        // Проверяем, есть ли текущий день в списке разрешенных
        String[] allowedDayArray = allowedDays.split(",");
        for (String day : allowedDayArray) {
            if (day.trim().equalsIgnoreCase(currentDay)) {
                return true;
            }
        }
        return false;
    }
}