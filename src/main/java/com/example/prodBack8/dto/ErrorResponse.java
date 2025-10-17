package com.example.prodBack8.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    @Schema(description = "Код ошибки")
    private String errorCode;
    @Schema(description = "Сообщение об ошибке")
    private String message;
}
