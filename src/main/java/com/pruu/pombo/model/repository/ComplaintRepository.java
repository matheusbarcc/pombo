package com.pruu.pombo.model.repository;

import com.pruu.pombo.model.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, String> {

    Optional<Complaint> findByPublicationId(String publicationId);
}
