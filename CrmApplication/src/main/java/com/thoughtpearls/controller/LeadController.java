package com.thoughtpearls.controller;

import com.thoughtpearls.dto.LeadRequestDto;
import com.thoughtpearls.dto.LeadResponseDto;
import com.thoughtpearls.dto.SearchParametersDto;
import com.thoughtpearls.service.LeadService;
import com.thoughtpearls.service.impl.LeadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lead")
public class LeadController {
    @Autowired
    private LeadService leadService;

    @PostMapping("/create")
    public ResponseEntity<LeadResponseDto> createLeadHandler(@RequestBody LeadRequestDto leadRequestDto, @RequestParam String token) {
        LeadResponseDto leadResponseDto = leadService.createLead(leadRequestDto,token);
        return new ResponseEntity<>(leadResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{leadId}")
    public ResponseEntity<LeadResponseDto> updateLeadHandler(@PathVariable long leadId, @RequestBody LeadRequestDto leadRequestDto,@RequestParam String token) {
        LeadResponseDto updatedLead = leadService.updateLead(leadId, leadRequestDto,token);
        return new ResponseEntity<>(updatedLead, HttpStatus.OK);
    }

    @DeleteMapping("delete/{leadId}")
    public ResponseEntity<LeadResponseDto> deleteLead(@PathVariable long leadId) {
        leadService.deleteLead(leadId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<LeadResponseDto>> getAllLeadsHandler(@RequestParam(defaultValue = "0") Integer pageNo,
                                                       @RequestParam(defaultValue = "2") Integer pageSize,
                                                       @RequestParam(defaultValue = "id") String sortBy){
    List<LeadResponseDto> leads = leadService.getAllLeads(pageNo, pageSize, sortBy);
    return new ResponseEntity<>(leads,HttpStatus.OK);
}



    @PostMapping("/searching")
    public ResponseEntity<Page<LeadResponseDto>> searchListWithList(@RequestBody SearchParametersDto searchParametersDto) {
        return ResponseEntity.ok(leadService.searchAndFilterInLead(searchParametersDto));
    }

    @GetMapping("/{leadId}")
    public ResponseEntity<LeadResponseDto> getLeadById(@PathVariable long leadId) {
            LeadResponseDto leadResponseDto = leadService.getLeadById(leadId);
            return new ResponseEntity<>(leadResponseDto, HttpStatus.OK);
    }
}
