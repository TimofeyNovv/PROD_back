package com.example.prodBack8.controller;

import com.example.prodBack8.dto.admin.CreateGroupRequest;
import com.example.prodBack8.dto.admin.SetGroupForUserRequest;
import com.example.prodBack8.dto.admin.UpdateGroupLimitsRequest;
import com.example.prodBack8.model.entity.group.GroupEntity;
import com.example.prodBack8.services.implServices.GroupServiceImpl;
import com.example.prodBack8.services.implServices.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "API для администратора")
public class AdminController {

    private final GroupServiceImpl groupService;
    private final UserServiceImpl userService;

    @Operation(
            summary = "обновить лимиты для группы",
            description = "обнвляет или устанавливает лимиты использования для группы",
            security = @SecurityRequirement(name = "jwtAuth")
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Успешно обновлено"),
                    @ApiResponse(responseCode = "404", description = "группа с таким idне найдена")
            }
    )
    @PatchMapping("/group/limits/{groupId}")
    public ResponseEntity<?> test(@PathVariable Long groupId, @RequestBody UpdateGroupLimitsRequest request) {
        groupService.updateGroupLimits(groupId, request);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Создать группу учеников с ресурсами",
            description = "Создает новую группу с указанными лимитами и распределением GPU",
            security = @SecurityRequirement(name = "jwtAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Группа успешно создана"),
            @ApiResponse(responseCode = "403", description = "нет прав доступа")
    })
    @PostMapping("/group")
    public ResponseEntity<?> createResourceGroup(@RequestBody CreateGroupRequest request) {
        groupService.create(request);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "установить группу пользователю",
            description = "устанавливает группу пользователю по имени пользователя и имени группы",
            security = @SecurityRequirement(name = "jwtAuth")
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "успешно установленна"),
                    @ApiResponse(responseCode = "404", description = "не найдена группа или пользователь")
            }
    )
    @PostMapping("/user/group")
    public ResponseEntity<?> setGroupForUser(@RequestBody SetGroupForUserRequest request) {
        userService.setGroupForUser(request);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получить список всех групп",
            security = @SecurityRequirement(name = "jwtAuth")
    )
    @GetMapping("/group/all")
    public List<GroupEntity> getAllGroups() {
        return groupService.getAllGroups();
    }


}