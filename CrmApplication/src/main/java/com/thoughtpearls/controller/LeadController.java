package com.thoughtpearls.controller;

import com.thoughtpearls.dto.LeadRequestDto;
import com.thoughtpearls.dto.LeadResponseDto;
import com.thoughtpearls.dto.SearchParametersDto;
import com.thoughtpearls.enums.LeadType;
import com.thoughtpearls.enums.Status;
import com.thoughtpearls.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/lead")
public class LeadController {
    @Autowired
    private LeadService leadService;

    @PostMapping("/create")
    public ResponseEntity<LeadResponseDto> createLeadHandler(@RequestBody LeadRequestDto leadRequestDto) {
        LeadResponseDto leadResponseDto = leadService.createLead(leadRequestDto);
        return new ResponseEntity<>(leadResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{leadId}")
    public ResponseEntity<LeadResponseDto> updateLeadHandler(@PathVariable long leadId, @RequestBody LeadRequestDto leadRequestDto) {
        LeadResponseDto updatedLead = leadService.updateLead(leadId, leadRequestDto);
        return new ResponseEntity<>(updatedLead, HttpStatus.OK);
    }

    @DeleteMapping("delete/{leadId}")
    public ResponseEntity<LeadResponseDto> deleteLead(@PathVariable long leadId) {
        leadService.deleteLead(leadId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<LeadResponseDto>> searchLeads(
//            @RequestParam(required = false) String leadName,
//            @RequestParam(required = false) Status leadStatus,
//            @RequestParam(required = false) LeadType leadType){
//        List<LeadResponseDto> leads = leadService.findLeadsWithFiltering(leadName, leadStatus, leadType);
//        return new ResponseEntity<>(leads,HttpStatus.OK);
//    }
//
//    @GetMapping("/sort")
//    public ResponseEntity<List<LeadResponseDto>> sortLeads(@RequestParam(defaultValue = "0") Integer pageNo,
//                                                           @RequestParam(defaultValue = "2") Integer pageSize,
//                                                           @RequestParam(defaultValue = "id") String sortBy){
//        List<LeadResponseDto> leads = leadService.sortLeads(pageNo, pageSize, sortBy);
//        return new ResponseEntity<>(leads,HttpStatus.OK);
//    }
//        @GetMapping("/findandsort")
//        public List<LeadResponseDto> findAndSortLeads(
//                @RequestParam(required = false) String input,
////                @RequestParam(required = false) Status leadStatus,
////                @RequestParam(required = false) LeadType leadType,
//                @RequestParam(defaultValue = "0") Integer pageNo,
//                @RequestParam(defaultValue = "2") Integer pageSize,
//                @RequestParam(defaultValue = "id") String sortBy) {
//
//            return leadService.findAndSortLeads(input, pageNo, pageSize, sortBy);
//        }
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
}
