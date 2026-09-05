package com.mich.ged.adapters.out.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mich.ged.adapters.out.persistence.entities.ServiceEntity;

@Repository
public interface SpringDataServiceRepository extends JpaRepository<ServiceEntity, Long> {
}