package com.example.prodBack8.services;

import com.example.prodBack8.model.entity.user.UserEntity;

public interface TaskService {
    void startGPUSession(UserEntity userEntity);
}
