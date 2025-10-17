package com.example.prodBack8.controller;

import com.example.prodBack8.dto.auth.LoginRequest;
import com.example.prodBack8.dto.auth.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "API для аутентификации и управления пользователями")
public class AuthController {


    /**
     * Регистрация нового пользователя
     * Доступ: ADMIN
     * Роль по умолчанию: STUDENT
     */
    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Создает нового пользователя."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные пользователя"),
            @ApiResponse(responseCode = "409", description = "Пользователь с таким email/username уже существует")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // TODO: создать нового пользователя с ролью STUDENT
        // TODO: возможно, требовать подтверждения от администратора
        return ResponseEntity.ok().build();
    }

    /**
     * Вход в систему
     * Доступ: все
     */
    @Operation(
            summary = "Вход в систему",
            description = "Аутентификация пользователя и получение JWT токена"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный вход"),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // TODO: аутентификация пользователя
        return ResponseEntity.ok().build();
    }

    /**
     * Получить текущего пользователя
     * Доступ: STUDENT, TEACHER, ADMIN
     */
    @Operation(
            summary = "Получить текущего пользователя",
            description = "Возвращает информацию о текущем аутентифицированном пользователе"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Информация о пользователе"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован")
    })
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        // TODO: вернуть информацию о текущем пользователе
        return ResponseEntity.ok().build();
    }
}