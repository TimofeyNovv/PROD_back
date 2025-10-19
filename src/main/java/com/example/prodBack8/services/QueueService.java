package com.example.prodBack8.services;

import com.example.prodBack8.model.entity.user.UserEntity;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface QueueService {
    void joinQueue(UserEntity currentUser);

    void leaveQueue(UserEntity userEntity);

    Integer getUserPosition(UserEntity currentUser);
}
