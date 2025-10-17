package com.example.prodBack8.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "API для пользователя")
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
    @PostMapping("startsession/{id}")
    public ResponseEntity<?> startGPUSession(@PathVariable Long userId){
        return ResponseEntity.ok().build();
    }

}
