package com.example.prodBack8.repository;

import com.example.prodBack8.model.entity.user.UserEntity;
import com.example.prodBack8.model.entity.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);

    @Query("SELECT u.role FROM UserEntity u WHERE u.id = :userId")
    Optional<UserRole> findRoleById(@Param("userId") Long userId);

    void deleteByUsername(String username);

    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.group.id = :groupId")
    Integer getCountUsersInCurrentGroup(@Param("groupId") Long groupId);
}
