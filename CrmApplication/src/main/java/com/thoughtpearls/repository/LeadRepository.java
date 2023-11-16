package com.thoughtpearls.repository;

import com.thoughtpearls.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadRepository extends JpaRepository<Lead,Long>, JpaSpecificationExecutor<Lead> {

}
