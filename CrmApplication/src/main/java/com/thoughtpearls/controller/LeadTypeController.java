package com.thoughtpearls.controller;

import com.thoughtpearls.dto.requestdto.LeadTypeRequestDto;
import com.thoughtpearls.dto.responsedto.LeadTypeResponseDto;
import com.thoughtpearls.exception.ExceptionMessage;
import com.thoughtpearls.service.LeadTypeService;
import com.thoughtpearls.dto.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leadType")
public class LeadTypeController {
    @Autowired
    private LeadTypeService leadTypeService;

    @PostMapping
    public ResponseEntity<LeadTypeResponseDto> createLeadType(@RequestBody LeadTypeRequestDto leadTypeRequestDto) {
        return new ResponseEntity<>(leadTypeService.createLeadType(leadTypeRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<SimplePage<LeadTypeResponseDto>> getAllLeadTypes(@RequestParam(defaultValue = "${pageNo}") Integer pageNo,
                                                           @RequestParam(defaultValue = "${pageSize}") Integer pageSize,
                                                           @RequestParam(defaultValue = "${sortById}") String sortBy) {
        return new ResponseEntity<>(leadTypeService.getAllLeadTypes(pageNo, pageSize, sortBy),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeadType(@PathVariable Long id) {
        leadTypeService.deleteLeadType(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
