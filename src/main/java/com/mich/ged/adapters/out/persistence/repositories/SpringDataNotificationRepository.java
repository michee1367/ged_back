package com.mich.ged.adapters.out.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mich.ged.adapters.out.persistence.entities.NotificationEntity;

@Repository
public interface SpringDataNotificationRepository extends JpaRepository<NotificationEntity, Long> {
    
}