package com.timechaser.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.timechaser.dto.TimesheetEntryProjectDto;
import com.timechaser.entity.Project;
import com.timechaser.entity.Timesheet;
import com.timechaser.entity.TimesheetEntry;
import com.timechaser.entity.TimesheetEntryProject;
import com.timechaser.entity.User;
import com.timechaser.exception.CreateException;
import com.timechaser.mapper.TimesheetEntryProjectMapper;
import com.timechaser.repository.TimesheetEntryProjectRespository;

@ExtendWith(MockitoExtension.class)
public class TimesheetEntryProjectServiceTest {
	@Mock
	private TimesheetEntryProjectRespository timesheetEntryProjectRespository;
	
	@InjectMocks
	private TimesheetEntryProjectService timesheetEntryProjectService;
	
	private TimesheetEntryProject timesheetEntryProject;
	private TimesheetEntryProjectDto timesheetEntryProjectDto;
	private TimesheetEntry timesheetEntry;
	private Timesheet timesheet;
	private Project project;
	private User user;
	
	@BeforeEach
	void setup() {
		timesheetEntryProject = new TimesheetEntryProject();
		
		project = new Project();
		project.setName("project");

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
		
		timesheetEntryProject.setTimesheetEntry(timesheetEntry);
		timesheetEntryProject.setHours(new BigDecimal(8));
		timesheetEntryProject.setProject(project);
		timesheetEntryProject.setId(1L);
		
		timesheetEntryProjectDto = TimesheetEntryProjectMapper.toDto(timesheetEntryProject);
	}
	
	@Test 
	void TimesheetEntryProjectService_Create_Success() {
		when(timesheetEntryProjectRespository.save(any(TimesheetEntryProject.class))).thenReturn(timesheetEntryProject);
		
		TimesheetEntryProjectDto response = timesheetEntryProjectService.create(timesheetEntryProjectDto);
		
		assertNotNull(response);
		assertEquals(user.getFirstName(), response.getTimesheetEntry().getTimesheet().getUser().getFirstName());
		assertEquals(project.getName(), response.getProject().getName());
	}
	
	@Test
	void TimesheetEntryProjectService_Create_Failure() {
		when(timesheetEntryProjectRespository.save(any(TimesheetEntryProject.class))).thenThrow(new IllegalArgumentException());
		
		assertThatThrownBy(() -> timesheetEntryProjectService.create(timesheetEntryProjectDto))
			.isInstanceOf(CreateException.class);
		
	}
}
