package com.example.prodBack8.model.entity.history;

import com.example.prodBack8.model.entity.BaseEntity;
import com.example.prodBack8.model.entity.group.GroupEntity;
import com.example.prodBack8.model.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class TaskEntity extends BaseEntity {

    private Integer countGPU;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupEntity group;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer usageDuration;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;
}
