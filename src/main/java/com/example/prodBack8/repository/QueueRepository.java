package com.example.prodBack8.repository;

import com.example.prodBack8.model.entity.queue.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRepository extends JpaRepository<QueueEntity, Long> {

}
