package com.example.prodBack8.services;

import com.example.prodBack8.dto.admin.CreateGroupRequest;
import com.example.prodBack8.dto.admin.UpdateGroupLimitsRequest;
import com.example.prodBack8.model.entity.group.GroupEntity;

import java.util.Optional;

public interface GroupService {
    void create(CreateGroupRequest request);
    void updateGroupLimits(Long groupId, UpdateGroupLimitsRequest request);
}
