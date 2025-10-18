package com.example.prodBack8.services;


import com.example.prodBack8.dto.admin.SetGroupForUserRequest;
import com.example.prodBack8.dto.admin.SetRemainingUsageTimeGPURequest;

public interface UserService {

    void setGroupForUser(SetGroupForUserRequest request);
    void setRemainingUsageTimeGPU(SetRemainingUsageTimeGPURequest request);
}
