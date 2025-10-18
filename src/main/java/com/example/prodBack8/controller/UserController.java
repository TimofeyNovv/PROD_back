package com.example.prodBack8.controller;

import com.example.prodBack8.model.entity.user.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "API для пользователя")
@SecurityRequirement(name = "jwtAuth")
public class UserController {

    @Operation(
            summary = "начать сессию с gpu",
            description = "начинает сессию с gpu"
    )
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", description = "Сессия успешно начата"),
                    @ApiResponse(responseCode = "409", description = "в данный момент нету свободной GPU")
            }
    )
    @PostMapping("/startsession")
    public ResponseEntity<?> startGPUSession(@AuthenticationPrincipal UserEntity currentUser) {

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "закончить сессию с gpu",
            description = "заканчивает сессию с gpu"
    )
    @PostMapping("/endsession")
    public ResponseEntity<?> endGPUSession(@AuthenticationPrincipal UserEntity currentUser) {
        //нужно записать время использования и время конца использования в таске
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
    public ResponseEntity<?> getGroupIdByUserId(@AuthenticationPrincipal UserEntity currentUser) {
        return ResponseEntity.ok().build();
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
    public ResponseEntity<?> getCountGPUByUserId(@AuthenticationPrincipal UserEntity currentUser) {
        //здесь дернуть группу, в которой находится пользователь, и потом у группы дернуть кол-во оставшихся gpu
        return ResponseEntity.ok().build();
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
    public ResponseEntity<?> getDistributionGroupByUserId(@AuthenticationPrincipal UserEntity currentUser) {
        //здесь дернуть группу пользователя и потом у группы дернуть distribution
        return ResponseEntity.ok().build();
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
    public ResponseEntity<?> getMaxSessionDurationGroupByUserId(@AuthenticationPrincipal UserEntity currentUser){
        //здесь дернуть группу пользователя у группы UsageLimit и у UsageLimit дернуть maxSessionDurationMinutes
        return ResponseEntity.ok().build();
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
    @GetMapping("/group/time/")
    public ResponseEntity<?> getAllowedTimeGroupByUserId(@AuthenticationPrincipal UserEntity currentUser){
        //дернуть группу у пользователя и у группы дернуть UsageLimit и у UsageLimit дернуть allowedDays dayStartTime dayEndTime
        return ResponseEntity.ok().build();
    }
}
