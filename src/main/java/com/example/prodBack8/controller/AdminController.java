package com.example.prodBack8.controller;

import com.example.prodBack8.dto.admin.AddUserRequest;
import com.example.prodBack8.dto.admin.CreateGroupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
            description = "Создает новую группу с указанными лимитами и распределением GPU"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Группа успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные группы")
    })
    @PostMapping("/add/group")
    public ResponseEntity<?> createResourceGroup(@RequestBody CreateGroupRequest request) {
        // TODO: создать новую группу с лимитами
        return ResponseEntity.ok().build();
    }

    /**
     * Добавить пользователя в группу
     */
    @Operation(
            summary = "Добавить пользователя в группу",
            description = "Добавляет существующего пользователя в группу и устанавливает пользователю группу"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь успешно добавлен"),
            @ApiResponse(responseCode = "404", description = "Пользователь или группа не найдены")
    })
    @PostMapping("/add/user")
    public ResponseEntity<?> addUserToGroup(@RequestBody AddUserRequest request) {
        // TODO: добавить пользователя и установит пользователю группу
        return ResponseEntity.ok().build();
    }

    /**
     * Получить статистику использования GPU
     */
    @Operation(
            summary = "Информация о группе",
            description = "Возвращает информацию о группе по её id"
    )
    @ApiResponse(responseCode = "200", description = "Статистика успешно получена")
    @GetMapping("/usage-stats/{id}")
    public ResponseEntity<?> getUsageStatistics(@PathVariable Integer id) {
        // TODO: вернуть информацию группы по id
        return ResponseEntity.ok().build();
    }

    /**
     * Получить историю всех запусков
     */

    //_______НУЖНО ПЕРЕДЕЛАТЬ КАК СКАЖЕТ ПРОДАКТ____________
    /*
    @Operation(
            summary = "История всех запусков",
            description = "Возвращает полную историю всех бронирований GPU с возможностью фильтрации"
    )
    @ApiResponse(responseCode = "200", description = "История успешно получена")
    @GetMapping("/reservations/history")
    public ResponseEntity<?> getAllReservationsHistory() {
        // TODO: вернуть полную историю бронирований
        return ResponseEntity.ok().build();
    }
    */


}