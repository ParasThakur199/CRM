package com.thoughtpearls.servicetest;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.thoughtpearls.dto.SimplePage;
import com.thoughtpearls.dto.requestdto.TechnologyRequestDto;
import com.thoughtpearls.dto.responsedto.TechnologyResponseDto;
import com.thoughtpearls.mapper.TechnologyMapper;
import com.thoughtpearls.model.Technology;
import com.thoughtpearls.repository.TechnologyRepository;
import com.thoughtpearls.service.impl.TechnologyServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TechnologyServiceTest {
	@Mock
    private TechnologyRepository technologyRepository;

    @Mock
    private TechnologyMapper technologyMapper;

    @InjectMocks
    private TechnologyServiceImpl technologyService;
    
    @Test
    void testCreateTechnology() {
        
        TechnologyRequestDto requestDto = new TechnologyRequestDto();
        requestDto.setCode("Java");
        
        Technology technology = new Technology();
        technology.setId(1L);
        technology.setCode("Java");
        
        TechnologyResponseDto expectedResponseDto = new TechnologyResponseDto();
        expectedResponseDto.setId(1L);
        expectedResponseDto.setCode("Java");
        
        when(technologyMapper.dtoToEntity(any(TechnologyRequestDto.class))).thenReturn(technology);
        when(technologyRepository.save(any(Technology.class))).thenReturn(technology);
        when(technologyMapper.entityToDto(any(Technology.class))).thenReturn(expectedResponseDto);

        TechnologyResponseDto responseDto = technologyService.createTechnology(requestDto);

        verify(technologyMapper, times(1)).dtoToEntity(requestDto);
        verify(technologyRepository, times(1)).save(technology);
        verify(technologyMapper, times(1)).entityToDto(technology);

    }
    
    @Test
    void testFindAllByIdIn() {

        List<Long> technologiesId = Arrays.asList(1L, 2L);
        List<Technology> expectedTech = Arrays.asList(
                new Technology(1L, "Code1", "Desc1", null),
                new Technology(2L, "Code2", "Desc2", null)
        );

        when(technologyRepository.findAllByIdIn(technologiesId)).thenReturn(expectedTech);

        List<Technology> result = technologyService.findAllByIdIn(technologiesId);

        assertEquals(expectedTech.size(), result.size());
        assertEquals(expectedTech, result);

        verify(technologyRepository, times(1)).findAllByIdIn(technologiesId);
    }
    
    
    @Test
    void testFindAllTechnology() {
    	int pageNo = 0;
        int pageSize = 10;
        String sortBy = "id";
        
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        
        Technology technology = new Technology();
        technology.setId(1L);
        technology.setCode("Java");
        
        TechnologyResponseDto technologyResponseDto = new TechnologyResponseDto();
        technologyResponseDto.setId(1L);
        technologyResponseDto.setCode("Java");
        
        List<Technology> expectedList = List.of(technology);
        Page<Technology> pagedResult = new PageImpl<>(expectedList,pageable,pageable.getPageSize());
        
        when(technologyRepository.findAll((Pageable)any())).thenReturn(pagedResult);
        when(technologyMapper.entityToDto(eq(technology))).thenReturn(technologyResponseDto);
       
        SimplePage<TechnologyResponseDto> actual = technologyService.findAllTechnology(pageNo, pageSize, sortBy);
        assertNotNull(actual);
        assertEquals(technologyResponseDto.getCode(),actual.getContent().get(0).getCode());
        verify(technologyRepository).findAll(eq(pageable));
        verify(technologyMapper).entityToDto(any());
    }
    
    
    
}
