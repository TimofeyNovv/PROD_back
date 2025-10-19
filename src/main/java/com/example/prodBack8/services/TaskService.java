package com.example.prodBack8.services;

import com.example.prodBack8.dto.TaskDTO;
import com.example.prodBack8.model.entity.history.TaskEntity;
import com.example.prodBack8.model.entity.user.UserEntity;

import java.util.List;

public interface TaskService {
    void startGPUSession(UserEntity userEntity);
    void endGPUSession(UserEntity userEntity);

    String getGroupNameByUserId(UserEntity entity);
    Integer getCurrentCountGPUByUserId(UserEntity entity);
    Integer getDistributionGroupByUserId(UserEntity entity);
    Integer getMaxSessionDurationGroupByUserId(UserEntity entity);
    String getAllowedTimeGroupByUserId(UserEntity entity);
    Integer getCountMembersGroupById(UserEntity entity);

    List<TaskDTO> getAllTasks();
}
