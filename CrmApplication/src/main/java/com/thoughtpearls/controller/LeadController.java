package com.thoughtpearls.controller;

import com.thoughtpearls.dto.LeadRequestDto;
import com.thoughtpearls.dto.LeadResponseDto;
import com.thoughtpearls.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class    LeadController {
    @Autowired
    private LeadService leadService;

    @PostMapping("/createLead")
    public ResponseEntity<LeadResponseDto> createLead(@RequestBody LeadRequestDto leadRequestDto){
        LeadResponseDto leadResponseDto = leadService.createLead(leadRequestDto);
        return new ResponseEntity<>(leadResponseDto, HttpStatus.CREATED);
    }
    @DeleteMapping("/deleteLead")
    public String deleteLead(@PathVariable long id){
        leadService.deleteLead(id);
        return "deleted lead successfully";
    }

}
