package com.example.prodBack8.services.implServices;

import com.example.prodBack8.exceptions.*;
import com.example.prodBack8.model.entity.group.GroupEntity;
import com.example.prodBack8.model.entity.group.UsageLimit;
import com.example.prodBack8.model.entity.history.TaskEntity;
import com.example.prodBack8.model.entity.history.TaskStatus;
import com.example.prodBack8.model.entity.queue.QueueEntity;
import com.example.prodBack8.model.entity.queue.QueueStatus;
import com.example.prodBack8.model.entity.user.UserEntity;
import com.example.prodBack8.repository.GroupRepository;
import com.example.prodBack8.repository.QueueRepository;
import com.example.prodBack8.repository.TaskRepository;
import com.example.prodBack8.repository.UserRepository;
import com.example.prodBack8.services.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final GroupRepository groupRepository;
    private final QueueRepository queueRepository;
    private final UserRepository userRepository;

    @Override
    public void startGPUSession(UserEntity userEntity) {

        Optional<TaskEntity> existingActiveTask = taskRepository.findByUserIdAndStatus(Long.valueOf(userEntity.getId()), TaskStatus.ACTIVE)
                .stream()
                .findFirst();

        if (existingActiveTask.isPresent()) {
            throw new ActiveSessionException("У вас уже есть активная сессия GPU");
        }

        if (userEntity.getGroup().getCurrentGPUCount() == 0) {
            throw new NoGPULeftException("у данной группы нету свободных GPU");
        }

        if (!isAllowedDayAndTime(userEntity.getGroup().getUsageLimit().getAllowedDays(),
                userEntity.getGroup().getUsageLimit().getDayStartTime(),
                userEntity.getGroup().getUsageLimit().getDayEndTime())) {
            throw new ForbiddenGPUInCurrentTimeException("Использование GPU не разрешено в текущее время");
        }


        TaskEntity task = TaskEntity.builder()
                .countGPU(1)
                .user(userEntity)
                .group(userEntity.getGroup())
                .startTime(LocalDateTime.now())
                .status(TaskStatus.ACTIVE)
                .build();

        GroupEntity group = userEntity.getGroup();
        group.setCurrentGPUCount(group.getCurrentGPUCount() - task.getCountGPU());
        groupRepository.save(group);
        taskRepository.save(task);

    }

    @Override
    public void endGPUSession(UserEntity userEntity) {
        TaskEntity activeTask = taskRepository.findByUserAndStatus(userEntity, TaskStatus.ACTIVE)
                .orElseThrow(() -> new NoActiveSessionException("У пользователя нет активной сессии"));

        activeTask.setEndTime(LocalDateTime.now());
        Duration usageDuration = Duration.between(activeTask.getStartTime(), activeTask.getEndTime());
        long minutes = usageDuration.toMinutes();
        activeTask.setUsageDuration((int) minutes);
        activeTask.setStatus(TaskStatus.COMPLETED);
        taskRepository.save(activeTask);

        GroupEntity group = userEntity.getGroup();
        group.setCurrentGPUCount(userEntity.getGroup().getCurrentGPUCount() + 1);
        groupRepository.save(group);

        UserEntity user = userEntity;
        user.setRemainingUsageTimeGPU(userEntity.getRemainingUsageTimeGPU() - activeTask.getUsageDuration());
        userRepository.save(user);

        distributeGPUToQueue(group.getId());
    }

    public void distributeGPUToQueue(Integer groupId) {
        log.info("Начинаем распределение GPU для группы {}", groupId);

        GroupEntity group = groupRepository.findById(Long.valueOf(groupId))
                .orElseThrow(() -> new GroupNotFoundException("Группа не найдена"));

        int availableGPUs = group.getCurrentGPUCount();

        if (availableGPUs <= 0) {
            log.info("Нет свободных GPU для распределения в группе {}", groupId);
            return;
        }

        List<QueueEntity> waitingQueue = queueRepository.findByGroupIdAndStatusOrderByPositionAsc(
                groupId, QueueStatus.WAITING);

        if (waitingQueue.isEmpty()) {
            log.info("В очереди группы {} нет ожидающих пользователей", groupId);
            return;
        }

        int allocatedGPUs = 0;


        for (QueueEntity queueItem : waitingQueue) {
            if (allocatedGPUs >= availableGPUs) {
                break;
            }

            try {
                UserEntity user = queueItem.getUser();

                // Запускаем GPU сессию для пользователя
                startGPUSessionForQueueUser(user, queueItem);
                allocatedGPUs++;

                log.info("Выделен GPU пользователю {} из очереди (позиция {})",
                        user.getUsername(), queueItem.getPosition());

            } catch (Exception e) {
                log.error("Ошибка при выделении GPU пользователю {}: {}",
                        queueItem.getUser().getUsername(), e.getMessage());
            }
        }

        log.info("Распределено {} GPU из {} доступных для группы {}",
                allocatedGPUs, availableGPUs, groupId);
    }
    private void startGPUSessionForQueueUser(UserEntity user, QueueEntity queueItem) {
        boolean hasActiveSession = taskRepository.findByUserAndStatus(user, TaskStatus.ACTIVE).isPresent();
        if (hasActiveSession) {
            throw new ActiveSessionException("У пользователя уже есть активная сессия");
        }

        TaskEntity task = TaskEntity.builder()
                .countGPU(1)
                .user(user)
                .group(user.getGroup())
                .startTime(LocalDateTime.now())
                .status(TaskStatus.ACTIVE)
                .build();

        GroupEntity group = user.getGroup();
        group.setCurrentGPUCount(group.getCurrentGPUCount() - 1);
        groupRepository.save(group);

        taskRepository.save(task);

        queueItem.setStatus(QueueStatus.ACTIVE);
        queueRepository.save(queueItem);

        log.info("Запущена GPU сессия для пользователя {} из очереди", user.getUsername());
    }

    @Override
    @Transactional(readOnly = true)
    public String getGroupNameByUserId(UserEntity entity) {
        return entity.getGroup().getName();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getCurrentCountGPUByUserId(UserEntity entity) {
        return entity.getGroup().getCurrentGPUCount();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getDistributionGroupByUserId(UserEntity entity) {
        return entity.getGroup().getDistribution();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getMaxSessionDurationGroupByUserId(UserEntity entity) {
        return entity.getGroup().getUsageLimit().getMaxSessionDurationMinutes();
    }

    @Override
    @Transactional(readOnly = true)
    public String getAllowedTimeGroupByUserId(UserEntity entity) {

        GroupEntity group = entity.getGroup();
        if (group == null) {
            throw new GroupNotFoundException("Пользователь не принадлежит к группе");
        }

        UsageLimit usageLimit = group.getUsageLimit();
        if (usageLimit == null) {
            return "Ограничения не установлены";
        }

        // Форматируем время в читаемый вид
        String days = formatDays(usageLimit.getAllowedDays());
        String timeRange = formatTimeRange(usageLimit.getDayStartTime(), usageLimit.getDayEndTime());

        return String.format("Дни: %s, Время: %s", days, timeRange);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getCountMembersGroupById(UserEntity currentUser) {
        if (currentUser.getGroup() == null) {
            throw new GroupNotFoundException("Пользователь не находится в группе");
        }

        Long groupId = Long.valueOf(currentUser.getGroup().getId());
        return userRepository.getCountUsersInCurrentGroup(groupId);
    }

    private String formatDays(String allowedDays) {
        if (allowedDays == null || allowedDays.trim().isEmpty()) {
            return "все дни";
        }

        return allowedDays
                .replace("MON", "Пн")
                .replace("TUE", "Вт")
                .replace("WED", "Ср")
                .replace("THU", "Чт")
                .replace("FRI", "Пт")
                .replace("SAT", "Сб")
                .replace("SUN", "Вс");
    }

    private String formatTimeRange(String startTime, String endTime) {
        if (startTime == null || endTime == null) {
            return "круглосуточно";
        }

        return String.format("%s - %s", startTime, endTime);
    }

    private boolean isAllowedDayAndTime(String allowedDays, String dayStartTime, String dayEndTime) {
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
