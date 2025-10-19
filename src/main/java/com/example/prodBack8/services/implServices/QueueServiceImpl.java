package com.example.prodBack8.services.implServices;

import com.example.prodBack8.exceptions.*;
import com.example.prodBack8.model.entity.group.GroupEntity;
import com.example.prodBack8.model.entity.group.UsageLimit;
import com.example.prodBack8.model.entity.queue.QueueEntity;
import com.example.prodBack8.model.entity.queue.QueueStatus;
import com.example.prodBack8.model.entity.user.UserEntity;
import com.example.prodBack8.repository.GroupRepository;
import com.example.prodBack8.repository.QueueRepository;
import com.example.prodBack8.services.QueueService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class QueueServiceImpl implements QueueService {

    private final QueueRepository queueRepository;
    private final GroupRepository groupRepository;

    @Override
    public void joinQueue(UserEntity currentUser) {

        GroupEntity group = groupRepository.getGroupById(currentUser.getGroup().getId())
                .orElseThrow(() -> new GroupNotFoundException("группа не найдена"));

        Integer userId = currentUser.getId();
        Integer groupId = group.getId();

        Optional<QueueEntity> existingQueueItem = queueRepository.findByUserIdAndGroupIdAndStatusIn(
                userId,
                groupId,
                List.of(QueueStatus.WAITING, QueueStatus.ACTIVE)
        );

        if (existingQueueItem.isPresent()) {
            QueueEntity existingItem = existingQueueItem.get();
            switch (existingItem.getStatus()) {
                case WAITING -> {
                    throw new YouAreInTheQueueException(
                            String.format("Вы уже находитесь в очереди на позиции %d", existingItem.getPosition())
                    );
                }
                case ACTIVE -> {
                    throw new ActiveSessionException("У вас уже есть активная сессия GPU");
                }

            }
        }
        if (!isGroupAvailable(group)) {
            throw new TimeGroupException("В данное время группа не доступна для использования");
        }

        Integer maxPosition = queueRepository.findMaxPositionByGroupIdAlternative(Long.valueOf(groupId));
        int newPosition = maxPosition + 1;

        QueueEntity queueItem = QueueEntity.builder()
                .user(currentUser)
                .group(group)
                .position(newPosition)
                .joinedAt(LocalDateTime.now())
                .status(QueueStatus.WAITING)
                .build();

        queueRepository.save(queueItem);

    }


    private boolean isGroupAvailable(GroupEntity group) {

        // Проверяем ограничения по времени, если они установлены
        UsageLimit usageLimit = group.getUsageLimit();
        if (usageLimit != null) {
            // Проверяем разрешенное время суток
            if (!usageLimit.isWithinAllowedTime()) {
                return false;
            }

            // Проверяем разрешенные дни недели
            if (!usageLimit.isWithinAllowedDays()) {
                return false;
            }
        }

        return true;
    }


    @Override
    public void leaveQueue(UserEntity userEntity) {
        Integer groupId = userEntity.getGroup().getId();
        Integer userId = userEntity.getId();

        Optional<QueueEntity> queueItemOpt = queueRepository.findByUserIdAndGroupIdAndStatusIn(
                userId,
                groupId,
                List.of(QueueStatus.WAITING)
        );

        if (queueItemOpt.isEmpty()) {
            throw new UserInQueueNotFoundException("Пользователь не найден в очереди");
        }

        QueueEntity queueItem = queueItemOpt.get();

        // Завершаем активную сессию
        Integer position = queueItem.getPosition();

        // Обновляем статус на CANCELLED
        queueItem.setStatus(QueueStatus.CANCELLED);
        queueRepository.save(queueItem);

        // Обновляем позиции остальных пользователей в очереди
        queueRepository.updatePositionsAfterRemoval(Long.valueOf(groupId), position);

        log.info("Пользователь {} покинул очередь группы {} с позиции {}",
                userId, groupId, position);

    }

    @Override
    public Integer getUserPosition(UserEntity currentUser) {
        try {
            Optional<QueueEntity> queueItemOpt = currentUser.getQueueItems().stream()
                    .filter(item -> item.getStatus() == QueueStatus.WAITING)
                    .findFirst();
            Integer position = queueItemOpt.get().getPosition();
            return position;
        } catch (NoSuchElementException exception) {
            throw new UserInQueueNotFoundException("пользователь не находится в очереди");
        }

    }


    // Получаем актуальное количество в очереди
    //Long currentQueueSize = queueRepository.countByGroupIdAndStatus(Long.valueOf(groupId), QueueStatus.WAITING);


}
