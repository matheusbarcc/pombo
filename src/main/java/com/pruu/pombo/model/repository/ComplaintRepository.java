package com.pruu.pombo.model.repository;

import com.pruu.pombo.model.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, String> {

    Complaint findByPruuId(String pruuId);
}
