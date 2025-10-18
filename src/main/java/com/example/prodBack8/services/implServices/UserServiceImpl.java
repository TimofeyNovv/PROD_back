package com.example.prodBack8.services.implServices;

import com.example.prodBack8.dto.admin.SetGroupForUserRequest;
import com.example.prodBack8.dto.admin.SetRemainingUsageTimeGPURequest;
import com.example.prodBack8.exceptions.GroupNotFoundException;
import com.example.prodBack8.exceptions.UserNotFoundException;
import com.example.prodBack8.model.entity.group.GroupEntity;
import com.example.prodBack8.model.entity.user.UserEntity;
import com.example.prodBack8.repository.GroupRepository;
import com.example.prodBack8.repository.UserRepository;
import com.example.prodBack8.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Override
    public void setGroupForUser(SetGroupForUserRequest request) {
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("user with name - " + request.getUsername() + " not found"));

        GroupEntity groupEntity = groupRepository.getGroupByName(request.getGroupName())
                .orElseThrow(() -> new GroupNotFoundException("group with name - " + request.getGroupName() + " not found"));
        user.setGroup(groupEntity);
        if (user.getRemainingUsageTimeGPU() == null) {
            user.setRemainingUsageTimeGPU(groupEntity.getUsageLimit().getMaxSessionDurationMinutes());
        } else {
            user.setRemainingUsageTimeGPU(user.getRemainingUsageTimeGPU() + groupEntity.getUsageLimit().getMaxSessionDurationMinutes());
        }
        userRepository.save(user);
    }

    @Override
    public void setRemainingUsageTimeGPU(SetRemainingUsageTimeGPURequest request) {
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("user with name - " + request.getUsername() + " not found"));
        if (user.getRemainingUsageTimeGPU() == null) {
            user.setRemainingUsageTimeGPU(request.getTimeMinutes());
        } else {
            user.setRemainingUsageTimeGPU(user.getRemainingUsageTimeGPU() + request.getTimeMinutes());
        }
        userRepository.save(user);
    }


}
