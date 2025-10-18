package com.example.prodBack8.services.implServices;

import com.example.prodBack8.dto.admin.CreateGroupRequest;
import com.example.prodBack8.dto.admin.UpdateGroupLimitsRequest;
import com.example.prodBack8.exceptions.GroupAlreadyExistsException;
import com.example.prodBack8.exceptions.GroupNotFoundException;
import com.example.prodBack8.model.entity.group.GroupEntity;
import com.example.prodBack8.model.entity.group.UsageLimit;
import com.example.prodBack8.repository.GroupRepository;
import com.example.prodBack8.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository repository;

    @Override
    public void create(CreateGroupRequest request) {
        String nameGroup = request.getNameGroup();
        if (repository.existsByName(nameGroup)) {
            throw new GroupAlreadyExistsException("group with name - " + nameGroup + "already exists");
        }
        GroupEntity entity = GroupEntity.builder()
                .name(nameGroup)
                .GPUcount(request.getGPUcount())
                .distribution(request.getDistribution())
                .build();
        repository.save(entity);
    }

    @Override
    public void updateGroupLimits(Long groupId, UpdateGroupLimitsRequest request) {
        GroupEntity groupEntity = repository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("group with id - " + groupId + " not found"));
        UsageLimit usageLimit = UsageLimit.builder()
                .maxSessionDurationMinutes(request.getMaxSessionDurationMinutes())
                .allowedDays(request.getAllowedDays())
                .dayStartTime(request.getDayStartTime())
                .dayEndTime(request.getDayEndTime())
                .build();

        groupEntity.setUsageLimit(usageLimit);
        repository.save(groupEntity);
    }
}
