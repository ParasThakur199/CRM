package com.thoughtpearls.servicetest;

import com.thoughtpearls.config.JwtService;
import com.thoughtpearls.mapper.LeadMapper;
import com.thoughtpearls.repository.LeadRepository;
import com.thoughtpearls.service.impl.LeadServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LeadServiceTest {

    @Mock
    private LeadRepository leadRepository;

    @InjectMocks
    private LeadServiceImpl leadService;

    @Mock
    private LeadMapper leadMapper;

    @Mock
    private JwtService jwtService;

//    @Test
//    void testDeleteLead() {
//        long leadId = 1L;
//        assertDoesNotThrow(() -> leadService.deleteLead(leadId));
//        verify(leadRepository).deleteById(leadId);
//    }
//
//    @Test
//    void testFindLeadById() {
//        long leadId = 1L;
//        Lead lead = new Lead();
//        when(leadRepository.findById(leadId)).thenReturn(Optional.of(lead));
//        Lead result = leadService.findLeadById(leadId);
//        verify(leadRepository).findById(leadId);
//        assertEquals(lead, result);
//    }
//
//    @Test
//    void testGetAllLeads() {
//
//        Integer pageNo = 0;
//        Integer pageSize = 10;
//        String sortBy = "leadName";
//
//        List<Lead> leads = new ArrayList<>();
//
//        Lead lead1 = new Lead();
//        lead1.setId(1L);
//        lead1.setLeadName("Lead 1");
//
//        Lead lead2 = new Lead();
//        lead2.setId(2L);
//        lead2.setLeadName("Lead 2");
//
//        leads.add(lead1);
//        leads.add(lead2);
//
//        Page<Lead> mockPagedResult = new PageImpl<>(leads);
//        when(leadRepository.findAll(any(PageRequest.class))).thenReturn(mockPagedResult);
//        List<LeadResponseDto> result = leadService.getAllLeads(pageNo, pageSize, sortBy);
//
//        verify(leadRepository).findAll(PageRequest.of(pageNo, pageSize, Sort.by(sortBy)));
//
//        assertNotNull(result);
//    }
//    @Test
//    void testCreateLead(){
//        try {
//            LeadRequestDto leadRequestDto = new LeadRequestDto();
//            leadRequestDto.setLeadName("lead1");
//
//            User user = new User();
//            user.setId(2L);
//
//            String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXJhc2phbXdhbDE5QGdtYWlsLmNvbSIsImp0aSI6IjIiLCJpYXQiOjE3MDA2MzY3MzEsImV4cCI6MTcwMDYzODE3MX0.FpqOTCLBrfZaPfz0e7OkJvq-5Vx-Y2L6uYJN1urqP0U";
//
//            LeadResponseDto expectedResponseDto = new LeadResponseDto();
//            expectedResponseDto.setId(1L);
//            expectedResponseDto.setLeadName("lead1");
//            expectedResponseDto.setCreatedBy(String.valueOf(user.getId()));
//            expectedResponseDto.setCreatedOn(LocalDateTime.now());
//
//            when(jwtService.getUserDetailsFromToken(token)).thenReturn(user);
//
//            Lead lead = new Lead();
//            when(leadMapper.dtoToEntity(leadRequestDto)).thenReturn(lead);
//            when(leadRepository.save(any(Lead.class))).thenReturn(lead);
//
//            when(leadService.createLead(leadRequestDto, token)).thenReturn(expectedResponseDto);
//
//            LeadResponseDto responseDto = leadService.createLead(leadRequestDto, token);
//
//            assertNotNull(responseDto);
//            assertEquals(expectedResponseDto.getId(), responseDto.getId());
//            assertEquals(expectedResponseDto.getLeadName(), responseDto.getLeadName());
//            assertEquals(expectedResponseDto.getCreatedBy(), responseDto.getCreatedBy());
//            assertEquals(expectedResponseDto.getCreatedOn(), responseDto.getCreatedOn());
//
//        } catch (Exception e) {
//            fail("Unexpected exception: " + e.getMessage());
//        }
//    }

//    @Test
//    void testUpdateLead(){
//        long leadId = 1L;
//        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXJhc2phbXdhbDE5QGdtYWlsLmNvbSIsImp0aSI6IjIiLCJpYXQiOjE3MDA2MzY3MzEsImV4cCI6MTcwMDYzODE3MX0.FpqOTCLBrfZaPfz0e7OkJvq-5Vx-Y2L6uYJN1urqP0U";
//        LeadRequestDto leadRequestDto = mock(LeadRequestDto.class);
//
//        User user = new User();
//        user.setId(1L);
//
//        Lead leadToUpdate = new Lead();
//        leadToUpdate.setId(leadId);
//
//        Lead updatedLead = new Lead();
//        updatedLead.setId(leadId);
//        updatedLead.setLeadName("Updated Lead");
//        updatedLead.setDescription("Updated Description");
//        updatedLead.setReminderDate(new Date());
//        updatedLead.setUpdatedOn(LocalDateTime.now());
//        updatedLead.setUpdatedBy(user.getId());
//
//        when(jwtService.getUserDetailsFromToken(token)).thenReturn(user);
//        when(leadRepository.findById(leadId)).thenReturn(Optional.of(leadToUpdate));
//        when(leadRepository.save(any(Lead.class))).thenReturn(updatedLead);
//        when(leadMapper.updateEntityFromDto(leadRequestDto, leadToUpdate)).thenReturn(updatedLead);
//        when(leadMapper.entityToDto(updatedLead)).thenReturn(new LeadResponseDto());
//
//        LeadResponseDto leadResponseDto = leadService.updateLead(leadId, leadRequestDto, token);
//
//        assertNotNull(leadResponseDto);
//        assertEquals(1L,updatedLead.getId());
//        assertEquals("Updated Lead", updatedLead.getLeadName());
//        assertEquals("Updated Description", updatedLead.getDescription());
//        assertNotNull(updatedLead.getUpdatedOn());
//        assertEquals(user.getId(), updatedLead.getUpdatedBy());
//
//        verify(leadRepository, times(1)).save(any(Lead.class));
//    }

//    @Test
//    void testSearchAndFilterInLead() {
//        // Mock input parameters
//        SearchParametersDto searchParametersDto = new SearchParametersDto();
//        searchParametersDto.setSearchString("Lead1");
//        searchParametersDto.setSortBy("leadName");
//        searchParametersDto.setSortOrder("ASC");
//        searchParametersDto.setPageNo(0);
//        searchParametersDto.setPageSize(2);
//
//        List<Lead> mockLeads = Arrays.asList(
//                new Lead(1L, "Lead1", "Description1", "Link1", Status.LEVEL1, LeadType.UPWORK,
//                        new Date(), "ReminderTopic1", null, null),
//                new Lead(2L, "Lead2", "Description2", "Link2", Status.LEVEL2, LeadType.LINKEDIN,
//                        new Date(), "ReminderTopic2", null, null)
//        );
//
//        List<LeadResponseDto> mockDtoList = Arrays.asList(
//                new LeadResponseDto(1L, "Lead1", "Description1", "Link1", null, null,
//                        null, null, Status.LEVEL1, LeadType.UPWORK,null,null),
//                new LeadResponseDto(2L,  "Lead2", "Description1", "Link1", null, null,
//                        null, null, Status.LEVEL2, LeadType.LINKEDIN,null,null)
//        );
//
//        Pageable pageable = Pageable.unpaged();
//        Page<Lead> mockPage = new PageImpl<>(mockLeads, pageable, mockLeads.size());
//
//        when(leadRepository.findAll((Specification<Lead>) any(), (Pageable) any())).thenReturn((Page<Lead>) mockPage);
//        lenient().when(leadMapper.entityToDto(any(Lead.class))).thenAnswer(invocation -> {
//            Lead lead = invocation.getArgument(0);
//            LeadResponseDto responseDto = new LeadResponseDto();
//            responseDto.setId(lead.getId());
//            responseDto.setLeadName(lead.getLeadName());
//            return responseDto;
//        });
//
//        Page<LeadResponseDto> result = leadService.searchAndFilterInLead(searchParametersDto);
//
//        assertEquals(mockDtoList.size(), result.getSize());
//        assertEquals(mockPage.getTotalElements(), result.getTotalElements());
//
//        verify(leadRepository).findAll((Specification<Lead>) any(), (Pageable) any());
//    }



}
