package com.example.prodBack8.services.implServices;

import com.example.prodBack8.exceptions.NoGPULeftException;
import com.example.prodBack8.model.entity.history.TaskEntity;
import com.example.prodBack8.model.entity.history.TaskStatus;
import com.example.prodBack8.model.entity.user.UserEntity;
import com.example.prodBack8.repository.TaskRepository;
import com.example.prodBack8.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    @Override
    public void startGPUSession(UserEntity userEntity) {

        if (userEntity.getGroup().getGPUcount() == 0){
            throw new NoGPULeftException("у данной группы нету свободных GPU");
        }

        if (!isAllowedDayAndTime(userEntity.getGroup().getUsageLimit().getAllowedDays(),
                userEntity.getGroup().getUsageLimit().getDayStartTime(),
                userEntity.getGroup().getUsageLimit().getDayEndTime())) {
            throw new NoGPULeftException("Использование GPU не разрешено в текущее время");
        }

        TaskEntity task = TaskEntity.builder()
                .countGPU(1)
                .user(userEntity)
                .group(userEntity.getGroup())
                .startTime(LocalDateTime.now())
                .status(TaskStatus.ACTIVE)
                .build();

        taskRepository.save(task);
    }

    private boolean isAllowedDayAndTime(String allowedDays, String dayStartTime, String dayEndTime){
        if (!isCurrentDayAllowed(allowedDays)) {
            return false;
        }

        return isCurrentTimeAllowed(dayStartTime, dayEndTime);
    }

    private boolean isCurrentDayAllowed(String allowedDays) {

        // Получаем текущий день недели в формате "MON", "TUE" и т.д.
        String currentDay = LocalDateTime.now().getDayOfWeek().toString().substring(0, 3);

        // Разделяем строку по запятым и проверяем наличие текущего дня
        String[] daysArray = allowedDays.split(",");
        System.out.println(Arrays.toString(daysArray));
        for (String day : daysArray) {
            if (day.trim().equalsIgnoreCase(currentDay)) {
                return true;
            }
        }

        return false;
    }

    private boolean isCurrentTimeAllowed(String dayStartTime, String dayEndTime) {

        try {
            // Парсим время из строк "10:00"
            LocalTime startTime = LocalTime.parse(dayStartTime);
            LocalTime endTime = LocalTime.parse(dayEndTime);
            LocalTime currentTime = LocalTime.now();

            // Проверяем, что текущее время в разрешенном интервале
            return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
        } catch (Exception e) {
            // Если формат времени некорректный - разрешаем по умолчанию
            System.out.println("ошибка в isCurrentTimeAllowed в TaskServiceImpl");
            return true;
        }
    }

}
