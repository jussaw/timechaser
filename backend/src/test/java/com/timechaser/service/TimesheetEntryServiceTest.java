package com.timechaser.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
import com.timechaser.entity.Timesheet;
import com.timechaser.entity.TimesheetEntry;
import com.timechaser.entity.User;
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
	private User user;
	private Timesheet timesheet;
	
	@BeforeEach
	void setUp() {
		user = new User();
		user.setUsername("testuser");
		user.setPassword("password");
		user.setFirstName("First");
		user.setLastName("Last");
		
		
		timesheet = new Timesheet();
		timesheet.setUser(user);
		timesheet.setYear(2024);
		timesheet.setWeekNumber(20);
		timesheet.setTotalHours(new BigDecimal(40));
		
		timesheetEntry = new TimesheetEntry();
		timesheetEntry.setTimesheet(timesheet);
		timesheetEntry.setDate(LocalDate.of(2024, 8, 1));
		timesheetEntry.setHoursWorked(new BigDecimal(8));
		
		timesheetEntryDto = TimesheetEntryMapper.toDto(timesheetEntry);
	}
	
	@Test 
	void TimesheetEntryService_Create_Success() {
		when(timesheetEntryRepository.save(any(TimesheetEntry.class))).thenReturn(timesheetEntry);
		
		TimesheetEntryDto response = timesheetEntryService.create(timesheetEntryDto);
		
		assertNotNull(response);
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
		assertEquals(user.getFirstName(), result.getTimesheet().getUser().getFirstName());
		assertEquals(timesheetEntry.getHoursWorked(), result.getHoursWorked());
	}
	
	@Test 
	void TimesheetEntry_findById_NotExist() {
		when(timesheetEntryRepository.findById(2L)).thenThrow(new NotFoundException("Timesheet entry was not found"));
		
		assertThatThrownBy(() ->  timesheetEntryService.findById(2L))
		.isInstanceOf(NotFoundException.class);
	}
	
	
}
