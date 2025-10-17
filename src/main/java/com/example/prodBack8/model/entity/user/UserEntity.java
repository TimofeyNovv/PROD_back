package com.example.prodBack8.model.entity.user;

import com.example.prodBack8.model.entity.BaseEntity;
import com.example.prodBack8.model.entity.group.GroupEntity;
import com.example.prodBack8.model.entity.history.TaskEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_table")
public class UserEntity extends BaseEntity {

    private String firstname;

    private String lastname;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupEntity group; // Группа к которой привязан пользователь

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TaskEntity> reservations = new ArrayList<>();

    /*
    @OneToMany(mappedBy = "user")
    private List<QueueItem> queueItems = new ArrayList<>();
     */


}
