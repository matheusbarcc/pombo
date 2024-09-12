package com.pruu.pombo.model.repository;

import com.pruu.pombo.model.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, String>, JpaSpecificationExecutor<Publication> {
}
