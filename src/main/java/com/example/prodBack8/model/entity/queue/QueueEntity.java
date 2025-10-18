package com.example.prodBack8.model.entity.queue;

import com.example.prodBack8.model.entity.BaseEntity;
import com.example.prodBack8.model.entity.group.GroupEntity;
import com.example.prodBack8.model.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "queue_items")
public class QueueEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group;

    @Column(nullable = false)
    private Integer position;

    @Column(nullable = false)
    private LocalDateTime joinedAt; // Когда встал в очередь

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QueueStatus status; // WAITING, ACTIVE, CANCELLED, COMPLETED


    public Duration getWaitingTime() {
        return Duration.between(joinedAt, LocalDateTime.now());
    }

    public boolean isWaiting() {
        return this.status == QueueStatus.WAITING;
    }
}