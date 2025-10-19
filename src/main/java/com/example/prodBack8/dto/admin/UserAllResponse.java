package com.example.prodBack8.dto.admin;

import com.example.prodBack8.model.entity.group.GroupEntity;
import com.example.prodBack8.model.entity.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAllResponse {
    private Integer id;
    private String username;
    private String firstname;
    private String lastname;
    private UserRole role;
    private String nameGroup;
}
