package com.thoughtpearls.controller;

import com.thoughtpearls.dto.LeadRequestDto;
import com.thoughtpearls.dto.LeadResponseDto;
import com.thoughtpearls.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LeadController {
    @Autowired
    private LeadService leadService;

    @PostMapping("/create_lead")
    public ResponseEntity<LeadResponseDto> createLeadHandler(@RequestBody LeadRequestDto leadRequestDto) {
        LeadResponseDto leadResponseDto = leadService.createLead(leadRequestDto);
        return new ResponseEntity<>(leadResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/update_lead/{leadId}")
    public ResponseEntity<LeadResponseDto> updateLeadHandler(@PathVariable long leadId, @RequestBody LeadRequestDto leadRequestDto) {
        LeadResponseDto updatedLead = leadService.updateLead(leadId, leadRequestDto);
        return new ResponseEntity<>(updatedLead, HttpStatus.OK);
    }


    @GetMapping("/getAllLeads")
    public ResponseEntity<List<LeadResponseDto>> getAllLeadsHandler(){
        List<LeadResponseDto> getAllLeads = leadService.getAllLeads();
        return new ResponseEntity<>(getAllLeads, HttpStatus.FOUND);
    }

    @DeleteMapping("delete_lead/{leadId}")
    public ResponseEntity<LeadResponseDto> deleteLead(@PathVariable long leadId) {
        leadService.deleteLead(leadId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
