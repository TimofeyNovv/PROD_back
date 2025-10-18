package com.example.prodBack8.controller;

import com.example.prodBack8.dto.queue.QueueRequest;
import com.example.prodBack8.dto.queue.QueueResponse;
import com.example.prodBack8.services.implServices.QueueServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/queue")
@RequiredArgsConstructor
@Tag(name = "Queue Management", description = "API для управления очередями GPU")
public class QueueController {

    private final QueueServiceImpl queueService;

    @PostMapping("/join")
    @Operation(summary = "Встать в очередь на использование GPU")
    public ResponseEntity<QueueResponse> joinQueue(@RequestBody QueueRequest request) {
        return null;
    }

    @PostMapping("/leave")
    @Operation(summary = "Покинуть очередь")
    public ResponseEntity<QueueResponse> leaveQueue(@RequestBody QueueRequest request){
        return null;
    }
}
