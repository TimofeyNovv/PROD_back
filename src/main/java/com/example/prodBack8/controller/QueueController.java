package com.example.prodBack8.controller;

import com.example.prodBack8.dto.queue.QueueRequest;
import com.example.prodBack8.dto.queue.QueueResponse;
import com.example.prodBack8.dto.queue.QueueStatusResponse;
import com.example.prodBack8.model.entity.user.UserEntity;
import com.example.prodBack8.services.implServices.QueueServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/queue")
@RequiredArgsConstructor
@Tag(name = "Queue Management", description = "API для управления очередями GPU")
@SecurityRequirement(name = "jwtAuth")
public class QueueController {

    private final QueueServiceImpl queueService;

    @PostMapping("/join")
    @Operation(summary = "Встать в очередь на использование GPU")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "успешно поставлен в очередь"),
                    @ApiResponse(responseCode = "409", description = "уже есть активная сессия gpu"),
                    @ApiResponse(responseCode = "404", description = "уже находитесь в очереди"),
                    @ApiResponse(responseCode = "400", description = "в данное время группа не доступна")
            }
    )
    public ResponseEntity<?> joinQueue(@AuthenticationPrincipal UserEntity currentUser) {
        queueService.joinQueue(currentUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/leave")
    @Operation(summary = "Покинуть очередь")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "очередь успешно покинута"),
                    @ApiResponse(responseCode = "404", description = "пользователь не найден в очереди")
            }
    )
    public ResponseEntity<QueueResponse> leaveQueue(@AuthenticationPrincipal UserEntity userEntity) {
        queueService.leaveQueue(userEntity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{groupId}")
    @Operation(summary = "Получить статус очереди для группы")
    public ResponseEntity<QueueStatusResponse> getQueueStatus(
            @PathVariable Long groupId,
            @RequestParam Long userId
    ) {
        return null;
    }

    @GetMapping("/position")
    @Operation(summary = "Получить позицию пользователя в очереди")
    public ResponseEntity<Integer> getUserPosition(
            @RequestParam Long userId,
            @RequestParam Long groupId) {
        return null;
    }
}
