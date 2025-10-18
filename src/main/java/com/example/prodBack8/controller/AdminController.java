package com.example.prodBack8.controller;

import com.example.prodBack8.dto.admin.CreateGroupRequest;
import com.example.prodBack8.dto.admin.UpdateGroupLimitsRequest;
import com.example.prodBack8.model.entity.user.UserEntity;
import com.example.prodBack8.services.implServices.GroupServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "API для администратора")
public class AdminController {

    private final GroupServiceImpl groupService;

    @Operation(
            security = @SecurityRequirement(name = "jwtAuth")
    )
    @PatchMapping("/test/{groupId}")
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


    /*@Operation(
            security = @SecurityRequirement(name = "jwtAuth")
    )
    @PutMapping("groups/set/{groupId}")
    public ResponseEntity<?> updateGroupL(
            @PathVariable Long groupId,
            @RequestBody UpdateGroupLimitsRequest request
    ){
        groupService.updateGroupLimits(groupId, request);
        return ResponseEntity.ok().build();
    }


    @Operation(
            summary = "Обновить лимиты группы",
            description = "Устанавливает ограничения на использование GPU для группы: максимальное время сессии и разрешенные часы работы",
            security = @SecurityRequirement(name = "jwtAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Лимиты успешно обновлены"),
            @ApiResponse(responseCode = "404", description = "Группа не найдена"),
            @ApiResponse(responseCode = "403", description = "нет прав доступа")
    })
    @GetMapping("/groups/limits")
    public ResponseEntity<?> updateGroupLimits(
            @Parameter(description = "ID группы ресурсов") @PathVariable Long groupId,
            @RequestBody UpdateGroupLimitsRequest request, @AuthenticationPrincipal UserEntity currentUser, Authentication authentication) {
        System.out.println("Authentication: " + authentication);
        System.out.println("Authorities: " + authentication.getAuthorities());
        System.out.println("Principal: " + authentication.getPrincipal());

        System.out.println(currentUser.getUsername());
        //groupService.updateGroupLimits(1L, request);
        return ResponseEntity.ok().build();
    }*/
}