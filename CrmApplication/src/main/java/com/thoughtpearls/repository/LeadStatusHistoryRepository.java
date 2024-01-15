package com.thoughtpearls.repository;

import com.thoughtpearls.model.LeadStatusHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadStatusHistoryRepository extends JpaRepository<LeadStatusHistory, Long>, JpaSpecificationExecutor<LeadStatusHistory>, PagingAndSortingRepository<LeadStatusHistory, Long> {

    @Query("SELECT ls FROM LeadStatusHistory ls WHERE ls.lead.id = ?1")
    Page<LeadStatusHistory> findByLeadId(Long leadId, Pageable pagable);
}
