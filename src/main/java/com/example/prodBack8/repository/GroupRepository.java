package com.example.prodBack8.repository;

import com.example.prodBack8.model.entity.group.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    boolean existsByName(String name);

    Optional<GroupEntity> getGroupById(Integer id);

    Optional<GroupEntity> getGroupByName(String name);
}
