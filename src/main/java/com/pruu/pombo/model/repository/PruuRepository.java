package com.pruu.pombo.model.repository;

import com.pruu.pombo.model.entity.Pruu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PruuRepository extends JpaRepository<Pruu, UUID> {

    // Set<Pruu> findByUserId(UUID userId);
}
