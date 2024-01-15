package com.thoughtpearls.controller;

import com.thoughtpearls.dto.requestdto.RejectionRequestDto;
import com.thoughtpearls.dto.responsedto.RejectionResponseDto;
import com.thoughtpearls.service.RejectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rejection")
public class RejectionController {

    @Autowired
    private RejectionService rejectionService;

    @PostMapping("/{leadId}")
    public ResponseEntity<RejectionResponseDto> createRejection(@PathVariable Long leadId,@RequestBody RejectionRequestDto rejectionRequestDto) {
        return new ResponseEntity<>(rejectionService.createRejection(rejectionRequestDto, leadId), HttpStatus.CREATED);
    }
}
