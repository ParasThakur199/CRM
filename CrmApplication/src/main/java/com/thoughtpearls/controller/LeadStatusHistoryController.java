package com.thoughtpearls.controller;

import com.thoughtpearls.dto.responsedto.LeadStatusHistoryResponseDto;
import com.thoughtpearls.service.LeadStatusHistoryService;
import com.thoughtpearls.dto.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
public class LeadStatusHistoryController {

    @Autowired
    private LeadStatusHistoryService leadStatusHistoryService;

    @GetMapping("/{leadId}")
    public ResponseEntity<SimplePage<LeadStatusHistoryResponseDto>> getAllHistoryByLeadId(@PathVariable Long leadId,
                                                                                          @RequestParam(defaultValue = "${pageNo}") Integer pageNo,
                                                                                          @RequestParam(defaultValue = "${pageSize}") Integer pageSize,
                                                                                          @RequestParam(defaultValue = "${sortByDateTime}") String sortBy) {
        return new ResponseEntity<>(leadStatusHistoryService.getAllHistoryByLeadId(leadId, pageNo, pageSize, sortBy), HttpStatus.OK);
    }
}
