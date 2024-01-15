package com.thoughtpearls.controller;

import com.thoughtpearls.dto.requestdto.TechnologyRequestDto;
import com.thoughtpearls.dto.responsedto.TechnologyResponseDto;
import com.thoughtpearls.service.TechnologyService;
import com.thoughtpearls.dto.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Technology")
public class TechnologyController {
    @Autowired
    private TechnologyService technologyService;

    @PostMapping
    public ResponseEntity<TechnologyResponseDto> createTechnology(@RequestBody TechnologyRequestDto technologyRequestDto) {
        return new ResponseEntity<>(technologyService.createTechnology(technologyRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<SimplePage<TechnologyResponseDto>> findAllTechnology(@RequestParam(defaultValue = "${pageNo}") Integer pageNo,
                                                                                  @RequestParam(defaultValue ="${pageSize}") Integer pageSize,
                                                                                  @RequestParam(defaultValue = "${sortById}") String sortBy) {
        return new ResponseEntity<>(technologyService.findAllTechnology(pageNo, pageSize, sortBy), HttpStatus.OK);
    }
}