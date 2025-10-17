package com.example.prodBack8.model.entity.user;

import com.example.prodBack8.model.entity.BaseEntity;
import com.example.prodBack8.model.entity.group.GroupEntity;
import com.example.prodBack8.model.entity.history.TaskEntity;
import com.example.prodBack8.model.entity.history.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_table")
public class UserEntity extends BaseEntity implements UserDetails{

    private String username;

    private String firstname;

    private String lastname;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private GroupEntity group; // Группа к которой привязан пользователь

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TaskEntity> tasks = new ArrayList<>();

    public TaskEntity getCurrentTask() {
        return tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.ACTIVE)
                .findFirst()
                .orElse(null);
    }


    @Override
    public String getUsername() {
        return username;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    /*
    @OneToMany(mappedBy = "user")
    private List<QueueItem> queueItems = new ArrayList<>();
     */


}
