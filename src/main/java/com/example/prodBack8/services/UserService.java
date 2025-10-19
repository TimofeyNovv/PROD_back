package com.example.prodBack8.services;


import com.example.prodBack8.dto.UserRoleResponse;
import com.example.prodBack8.dto.admin.SetGroupForUserRequest;
import com.example.prodBack8.dto.admin.SetRemainingUsageTimeGPURequest;
import com.example.prodBack8.dto.admin.UserAllResponse;
import com.example.prodBack8.model.entity.user.UserEntity;
import com.example.prodBack8.model.entity.user.UserRole;

import java.util.List;

public interface UserService {

    void setGroupForUser(SetGroupForUserRequest request);
    void setRemainingUsageTimeGPU(SetRemainingUsageTimeGPURequest request);
    List<UserAllResponse> getAllUsers();
    UserRole getUserRole(UserEntity userEntity);
    void deleteUserByUsername(String username);
    Integer getCountUsersInCurrentGroup(Integer groupId);

}
