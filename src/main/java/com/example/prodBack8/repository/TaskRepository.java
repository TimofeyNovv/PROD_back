package com.example.prodBack8.repository;

import com.example.prodBack8.model.entity.history.TaskEntity;
import com.example.prodBack8.model.entity.history.TaskStatus;
import com.example.prodBack8.model.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    Optional<TaskEntity> findByUserAndStatus(UserEntity userEntity, TaskStatus status);
}
