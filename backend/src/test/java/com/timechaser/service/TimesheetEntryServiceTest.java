package com.timechaser.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.timechaser.dto.TimesheetEntryDto;
import com.timechaser.entity.Project;
import com.timechaser.entity.Timesheet;
import com.timechaser.entity.TimesheetEntry;
import com.timechaser.exception.CreateException;
import com.timechaser.exception.NotFoundException;
import com.timechaser.mapper.TimesheetEntryMapper;
import com.timechaser.repository.TimesheetEntryRepository;


@ExtendWith(MockitoExtension.class)
public class TimesheetEntryServiceTest {
	
	@Mock
	private TimesheetEntryRepository timesheetEntryRepository;
	
	@InjectMocks
	private TimesheetEntryService timesheetEntryService;
	
	private TimesheetEntry timesheetEntry;
	private TimesheetEntryDto timesheetEntryDto;
	private Timesheet timesheet;
	private Project project;
	
	@BeforeEach
	void setUp() {
		timesheet = new Timesheet();
		timesheet.setId(1L);
		
		project = new Project();
		project.setId(1L);
		
		timesheetEntry = new TimesheetEntry();
		timesheetEntry.setTimesheet(timesheet);
		timesheetEntry.setProject(project);
		timesheetEntry.setDate(LocalDate.of(2024, 8, 1));
		timesheetEntry.setHoursWorked(new BigDecimal(8));
		
		timesheetEntryDto = TimesheetEntryMapper.toDto(timesheetEntry);
	}
	
	@Test 
	void TimesheetEntryService_Create_Success() {
		when(timesheetEntryRepository.save(any(TimesheetEntry.class))).thenReturn(timesheetEntry);
		
		TimesheetEntryDto response = timesheetEntryService.create(timesheetEntryDto);
		
		assertNotNull(response);
		assertEquals(timesheetEntry.getId(), response.getId());
		assertEquals(timesheetEntry.getDate(), response.getDate());
		assertEquals(timesheetEntry.getHoursWorked(), response.getHoursWorked());
	}
	
	@Test 
	void TimesheetEntryService_Create_Failure() {
		when(timesheetEntryRepository.save(any(TimesheetEntry.class))).thenThrow(new IllegalArgumentException());
		
		assertThatThrownBy(() -> timesheetEntryService.create(timesheetEntryDto))
			.isInstanceOf(CreateException.class);
	}
	
	@Test 
	void TimesheetEntry_findById_Success() {
		when(timesheetEntryRepository.findById(1L)).thenReturn(Optional.of(timesheetEntry));
		
		TimesheetEntry result = timesheetEntryService.findById(1L).get();
		
		assertNotNull(result);
		assertEquals(timesheetEntry.getHoursWorked(), result.getHoursWorked());
	}
	
	@Test 
	void TimesheetEntry_findById_NotExist() {
		when(timesheetEntryRepository.findById(1L)).thenThrow(new NotFoundException("Timesheet entry was not found"));
		
		assertThatThrownBy(() ->  timesheetEntryService.findById(1L))
		.isInstanceOf(NotFoundException.class);
	}
	
	@Test
	void TimesheetEntry_Delete_Success() {
		timesheetEntryService.deleteById(1L);
		verify(timesheetEntryRepository, times(1)).deleteById(1L);
	}
	
    
}
