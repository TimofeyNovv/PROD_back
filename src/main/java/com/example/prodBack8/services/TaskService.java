package com.example.prodBack8.services;

import com.example.prodBack8.model.entity.user.UserEntity;

public interface TaskService {
    void startGPUSession(UserEntity userEntity);
    void endGPUSession(UserEntity userEntity);

    String getGroupNameByUserId(UserEntity entity);
    Integer getCurrentCountGPUByUserId(UserEntity entity);
    Integer getDistributionGroupByUserId(UserEntity entity);
    Integer getMaxSessionDurationGroupByUserId(UserEntity entity);
    String getAllowedTimeGroupByUserId(UserEntity entity);
    Long getCountMembersGroupById(UserEntity entity);
}
