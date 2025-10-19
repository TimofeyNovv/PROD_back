package com.example.prodBack8.dto;

import com.example.prodBack8.model.entity.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleResponse {
    private UserRole role;
}
