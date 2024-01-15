package com.thoughtpearls.controller;

import com.thoughtpearls.dto.requestdto.StatusRequestDto;
import com.thoughtpearls.dto.responsedto.StatusResponseDto;
import com.thoughtpearls.exception.ExceptionMessage;
import com.thoughtpearls.service.StatusService;
import com.thoughtpearls.dto.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @PostMapping
    public ResponseEntity<StatusResponseDto> createStatus(@RequestBody StatusRequestDto statusRequestDto)
    {
        return new ResponseEntity<>(statusService.createStatus(statusRequestDto),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<SimplePage<StatusResponseDto>> getAllStatus(@RequestParam(defaultValue = "${pageNo}") Integer pageNo,
                                                                     @RequestParam(defaultValue = "${pageSize}") Integer pageSize,
                                                                     @RequestParam(defaultValue = "${sortById}") String sortBy){
        return new ResponseEntity<>(statusService.getAllStatus(pageNo, pageSize, sortBy), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Long id)
    {
        statusService.deleteStatus(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
