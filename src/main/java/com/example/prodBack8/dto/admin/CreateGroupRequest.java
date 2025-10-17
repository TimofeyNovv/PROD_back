package com.example.prodBack8.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для создания новой группы")
public class CreateGroupRequest {

    private String nameGroup;//название группы
    private Integer GPUcount;//количество карточек на группу
    private Integer distribution;//распределение
}
