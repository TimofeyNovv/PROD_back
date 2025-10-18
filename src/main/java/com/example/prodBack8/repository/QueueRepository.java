package com.example.prodBack8.repository;

import com.example.prodBack8.model.entity.queue.QueueEntity;
import com.example.prodBack8.model.entity.queue.QueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QueueRepository extends JpaRepository<QueueEntity, Long> {

    Optional<QueueEntity> findByUserIdAndGroupIdAndStatusIn(
            Integer userId,
            Integer groupId,
            List<QueueStatus> statuses
    );

    default Integer findMaxPositionByGroupIdAlternative(Long groupId) {
        // Находим первую запись с максимальной позицией
        Optional<QueueEntity> topItem = findTopByGroupIdAndStatusOrderByPositionDesc(groupId, QueueStatus.WAITING);
        return topItem.map(QueueEntity::getPosition).orElse(0);
    }
    Optional<QueueEntity> findTopByGroupIdAndStatusOrderByPositionDesc(Long groupId, QueueStatus status);

    Long countByGroupIdAndStatus(Long groupId, QueueStatus status);

    @Modifying
    @Query("UPDATE QueueEntity q SET q.position = q.position - 1 " +
            "WHERE q.group.id = :groupId " +
            "AND q.position > :removedPosition " +
            "AND q.status = 'WAITING'")
    void updatePositionsAfterRemoval(@Param("groupId") Long groupId,
                                     @Param("removedPosition") Integer removedPosition);
}
