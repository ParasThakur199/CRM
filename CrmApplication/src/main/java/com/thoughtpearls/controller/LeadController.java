package com.thoughtpearls.controller;

import com.thoughtpearls.dto.requestdto.LeadRequestDto;
import com.thoughtpearls.dto.responsedto.LeadResponseDto;
import com.thoughtpearls.dto.requestdto.SearchParameterRequestDto;
import com.thoughtpearls.service.LeadService;
import com.thoughtpearls.dto.SimplePage;
import com.thoughtpearls.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lead")
public class LeadController {
    @Autowired
    private LeadService leadService;

    @PostMapping
    public ResponseEntity<LeadResponseDto> createLead(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,@RequestBody LeadRequestDto leadRequestDto) {
        CommonMethod.validateHeader(authorizationHeader);
        return new ResponseEntity<>(leadService.createLead(leadRequestDto, authorizationHeader), HttpStatus.CREATED);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<LeadResponseDto>> searchAndFilterLead(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,@RequestBody SearchParameterRequestDto searchParameterRequestDto) {
        CommonMethod.validateHeader(authorizationHeader);
        return ResponseEntity.ok(leadService.searchAndFilterInLead(searchParameterRequestDto, authorizationHeader));
    }

    @GetMapping
    public ResponseEntity<SimplePage<LeadResponseDto>> getAllLeads(@RequestParam(defaultValue = "${pageNo}") Integer pageNo,
                                                                   @RequestParam(defaultValue = "${pageSize}") Integer pageSize,
                                                                   @RequestParam(defaultValue = "${sortById}") String sortBy) {
        return new ResponseEntity<>(leadService.getAllLeads(pageNo, pageSize, sortBy), HttpStatus.OK);
    }

    @GetMapping("/{leadId}")
    public ResponseEntity<LeadResponseDto> getLeadById(@PathVariable Long leadId) {
        return new ResponseEntity<>(leadService.getLeadById(leadId), HttpStatus.OK);
    }

    @PutMapping("/{leadId}")
    public ResponseEntity<LeadResponseDto> updateLead(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,@PathVariable Long leadId, @RequestBody LeadRequestDto leadRequestDto) {
        CommonMethod.validateHeader(authorizationHeader);
        return new ResponseEntity<>(leadService.updateLead(leadId, leadRequestDto, authorizationHeader), HttpStatus.OK);
    }

    @DeleteMapping("/{leadId}")
    public ResponseEntity<Void> deleteLead(@PathVariable Long leadId) {
        leadService.deleteLead(leadId);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
