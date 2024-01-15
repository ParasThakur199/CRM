package com.thoughtpearls.repository;

import com.thoughtpearls.model.LeadType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeadTypeRepository extends JpaRepository<LeadType, Long> {
}
