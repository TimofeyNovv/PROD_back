package com.example.prodBack8.services.implServices;

import com.example.prodBack8.exceptions.NoGPULeftException;
import com.example.prodBack8.model.entity.history.TaskEntity;
import com.example.prodBack8.model.entity.history.TaskStatus;
import com.example.prodBack8.model.entity.user.UserEntity;
import com.example.prodBack8.repository.TaskRepository;
import com.example.prodBack8.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    @Override
    public void startGPUSession(UserEntity userEntity) {

        if (userEntity.getGroup().getGPUcount() == 0){
            throw new NoGPULeftException("у данной группы нету свободных GPU");
        }


        TaskEntity task = TaskEntity.builder()
                .countGPU(1)
                .user(userEntity)
                .group(userEntity.getGroup())
                .startTime(LocalDateTime.now())
                .status(TaskStatus.ACTIVE)
                .build();
    }
}
