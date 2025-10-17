package com.example.prodBack8.controller;

import com.example.prodBack8.dto.admin.CreateGroupRequest;
import com.example.prodBack8.dto.admin.UpdateGroupLimitsRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin", description = "API для администратора")
public class AdminController {

    /**
     * Создать группу ресурсов
     */
    @Operation(
            summary = "Создать группу учеников с ресурсами",
            description = "Создает новую группу с указанными лимитами и распределением GPU",
            security = @SecurityRequirement(name = "jwtAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Группа успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные группы"),
            @ApiResponse(responseCode = "403", description = "нет прав доступа")
    })
    @PostMapping("/group")
    public ResponseEntity<?> createResourceGroup(@RequestBody CreateGroupRequest request) {
        // TODO: создать новую группу с лимитами
        return ResponseEntity.ok().build();
    }


    /**
     * Обновить лимиты использования для группы
     */
    @Operation(
            summary = "Обновить лимиты группы",
            description = "Устанавливает ограничения на использование GPU для группы: максимальное время сессии и разрешенные часы работы",
            security = @SecurityRequirement(name = "jwtAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Лимиты успешно обновлены"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные лимитов"),
            @ApiResponse(responseCode = "404", description = "Группа не найдена"),
            @ApiResponse(responseCode = "403", description = "нет прав доступа")
    })
    @PutMapping("/groups/{groupId}/limits")
    public ResponseEntity<?> updateGroupLimits(
            @Parameter(description = "ID группы ресурсов") @PathVariable Long groupId,
            @Valid @RequestBody UpdateGroupLimitsRequest request) {

        // TODO: найти группу по ID
        // TODO: обновить лимиты в сущности группы
        // TODO: сохранить изменения
        // TODO: вернуть обновленную группу

        return ResponseEntity.ok().build();
    }




}