package com.example.prodBack8.services;

import com.example.prodBack8.dto.admin.CreateGroupRequest;
import com.example.prodBack8.dto.admin.UpdateGroupLimitsRequest;
import com.example.prodBack8.model.entity.group.GroupEntity;

import java.util.List;

public interface GroupService {
    GroupEntity create(CreateGroupRequest request);
    void updateGroupLimits(Long groupId, UpdateGroupLimitsRequest request);
    List<GroupEntity> getAllGroups();
}
