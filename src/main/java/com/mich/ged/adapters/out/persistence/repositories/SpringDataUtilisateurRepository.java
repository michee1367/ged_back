package com.mich.ged.adapters.out.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mich.ged.adapters.out.persistence.entities.UtilisateurEntity;

@Repository
public interface SpringDataUtilisateurRepository extends JpaRepository<UtilisateurEntity, Long> {
    
    Optional<UtilisateurEntity> findByPhoneNumber(String phoneNumber);

    Optional<UtilisateurEntity> findByEmail(String email);

    @Query("Select u From UtilisateurEntity u Where u.email = :identifier Or u.phoneNumber = :identifier ")
    Optional<UtilisateurEntity> findByEmailOrPhoneNumber(@Param("identifier") String identifier);

}