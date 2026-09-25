package com.mich.ged.adapters.out.persistence.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mich.ged.adapters.out.persistence.entities.ServiceEntity;

@Repository
public interface SpringDataServiceRepository extends JpaRepository<ServiceEntity, Long> {

    @Query("SELECT DISTINCT s FROM ServiceEntity s JOIN s.utilisateurs u WHERE u.service.id = :serviceId")
    Page<ServiceEntity> findAttachedServices(@Param("serviceId") Long serviceId, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT s.id) FROM ServiceEntity s JOIN s.utilisateurs u WHERE u.service.id = :serviceId")
    long countAttachedServices(@Param("serviceId") Long serviceId);
}