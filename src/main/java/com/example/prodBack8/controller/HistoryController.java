package com.example.prodBack8.controller;

import com.example.prodBack8.dto.TaskDTO;
import com.example.prodBack8.model.entity.history.TaskEntity;
import com.example.prodBack8.services.implServices.TaskServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@Tag(name = "Histry", description = "API для работы с историей")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwtAuth")
public class HistoryController {

    private final TaskServiceImpl taskService;
    @Operation(
            description = "получить всю историю"
    )
    @GetMapping("/all")
    public List<TaskDTO> getAllTasks(){
        return taskService.getAllTasks();
    }
}
