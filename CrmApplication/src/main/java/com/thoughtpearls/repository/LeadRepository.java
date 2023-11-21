package com.thoughtpearls.repository;

import com.thoughtpearls.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LeadRepository extends JpaRepository<Lead,Long>, JpaSpecificationExecutor<Lead>,PagingAndSortingRepository<Lead,Long>{
    List<Lead> findByReminderDate(Date currentDate);
}
