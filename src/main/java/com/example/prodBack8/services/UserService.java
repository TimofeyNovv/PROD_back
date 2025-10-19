package com.example.prodBack8.services;


import com.example.prodBack8.dto.admin.SetGroupForUserRequest;
import com.example.prodBack8.dto.admin.SetRemainingUsageTimeGPURequest;
import com.example.prodBack8.dto.admin.UserAllResponse;

import java.util.List;

public interface UserService {

    void setGroupForUser(SetGroupForUserRequest request);
    void setRemainingUsageTimeGPU(SetRemainingUsageTimeGPURequest request);
    List<UserAllResponse> getAllUsers();
}
