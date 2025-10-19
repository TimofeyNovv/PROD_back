package com.example.prodBack8.controller;

import com.example.prodBack8.dto.UserRoleResponse;
import com.example.prodBack8.model.entity.user.UserEntity;
import com.example.prodBack8.services.implServices.TaskServiceImpl;
import com.example.prodBack8.services.implServices.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "API для пользователя")
@SecurityRequirement(name = "jwtAuth")
@RequiredArgsConstructor
public class UserController {

    private final TaskServiceImpl taskService;
    private final UserServiceImpl userService;

    @Operation(
            summary = "начать сессию с gpu",
            description = "начинает сессию с gpu"
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Сессия успешно начата"),
                    @ApiResponse(responseCode = "404", description = "в данный момент нету свободной GPU"),
                    @ApiResponse(responseCode = "409", description = "уже есть активная сессия с GPU"),
                    @ApiResponse(responseCode = "400", description = "в текущее время нельзя начать сессию с GPU")}
    )
    @PostMapping("/startsession")
    public ResponseEntity<?> startGPUSession(@AuthenticationPrincipal UserEntity currentUser) {
        taskService.startGPUSession(currentUser);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "закончить сессию с gpu",
            description = "заканчивает сессию с gpu"
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "успешный конец сессии"),
                    @ApiResponse(responseCode = "404", description = "у пользователя не найдено активных сессий")
            }
    )
    @PostMapping("/endsession")
    public ResponseEntity<?> endGPUSession(@AuthenticationPrincipal UserEntity currentUser) {
        taskService.endGPUSession(currentUser);
        return ResponseEntity.ok().build();
    }


    @Operation(
            summary = "получить название группы",
            description = "получить название группы в которой находится пользователь"
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "успешно"),
            }
    )
    @GetMapping("/group/name")
    public ResponseEntity<Map<String, String>> getGroupName(@AuthenticationPrincipal UserEntity currentUser) {
        String groupName = taskService.getGroupNameByUserId(currentUser);
        return ResponseEntity.ok(Collections.singletonMap("groupName", groupName));
    }

    @Operation(
            summary = "получить количество доступных gpu",
            description = "получить количество доступных пользователю gpu"
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "успешно"),
            }
    )
    @GetMapping("/gpu/count")
    public ResponseEntity<Map<String, Integer>> getCurrentCountGPU(@AuthenticationPrincipal UserEntity currentUser) {
        Integer count = taskService.getCurrentCountGPUByUserId(currentUser);
        return ResponseEntity.ok(Collections.singletonMap("gpuCount", count));
    }

    @Operation(
            summary = "узнать какое распределение у группы",
            description = "узнать какое распределение у группы пользователя"
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "успешно"),
            }
    )
    @GetMapping("/group/distribution")
    public ResponseEntity<Map<String, Integer>> getDistributionGroup(@AuthenticationPrincipal UserEntity currentUser) {
        Integer distribution = taskService.getDistributionGroupByUserId(currentUser);
        return ResponseEntity.ok(Collections.singletonMap("distribution", distribution));
    }

    @Operation(
            summary = "узнать максимальное время выполнение задачи",
            description = "узнать максимальное время выполнение задачи в группе пользователя"
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "успешно"),
            }
    )
    @GetMapping("/group/maxsession")
    public ResponseEntity<Map<String, Integer>> getMaxSessionDurationGroup(@AuthenticationPrincipal UserEntity currentUser) {
        Integer maxSession = taskService.getMaxSessionDurationGroupByUserId(currentUser);
        return ResponseEntity.ok(Collections.singletonMap("maxSessionDuration", maxSession));
    }

    @Operation(
            summary = "узнать ограничения по времени суток",
            description = "узнать ограничения по времени суток у группы пользователя"
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "успешно"),
            }
    )
    @GetMapping("/group/time")
    public ResponseEntity<Map<String, String>> getAllowedTimeGroup(@AuthenticationPrincipal UserEntity currentUser) {
        String allowedTime = taskService.getAllowedTimeGroupByUserId(currentUser);
        return ResponseEntity.ok(Collections.singletonMap("allowedTime", allowedTime));
    }

    @Operation(
            summary = "узнать количество пользователей в текущей группе"
    )
    @GetMapping("group/count")
    public ResponseEntity<Map<String, Integer>> getCountMembersGroup(@AuthenticationPrincipal UserEntity currentUser) {
        Long count = Long.valueOf(taskService.getCountMembersGroupById(currentUser));
        return ResponseEntity.ok(Collections.singletonMap("membersCount", Math.toIntExact(count)));
    }

    @Operation(
            summary = "узнать статус пользователя"
    )
    @GetMapping("user/status")
    public ResponseEntity<UserRoleResponse> getRole(@AuthenticationPrincipal UserEntity currentUser){
        UserRoleResponse response = UserRoleResponse.builder()
                .role(currentUser.getRole())
                .build();

        return ResponseEntity.ok(response);
    }

}
