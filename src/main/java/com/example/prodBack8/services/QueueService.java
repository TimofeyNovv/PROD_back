package com.example.prodBack8.services;

import com.example.prodBack8.model.entity.user.UserEntity;

public interface QueueService {
    void joinQueue(UserEntity currentUser);
    void leaveQueue(UserEntity userEntity);
}
