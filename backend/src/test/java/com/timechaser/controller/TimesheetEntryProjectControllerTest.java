package com.timechaser.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timechaser.dto.TimesheetEntryProjectDto;
import com.timechaser.entity.Project;
import com.timechaser.entity.Timesheet;
import com.timechaser.entity.TimesheetEntry;
import com.timechaser.entity.TimesheetEntryProject;
import com.timechaser.entity.User;
import com.timechaser.enums.UserRoles;
import com.timechaser.mapper.TimesheetEntryProjectMapper;
import com.timechaser.service.TimesheetEntryProjectService;

@ContextConfiguration
@SpringBootTest
@WithMockUser(authorities = { UserRoles.EMPLOYEE, UserRoles.EMPLOYEE })
public class TimesheetEntryProjectControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TimesheetEntryProjectService timesheetEntryProjectService;

	private TimesheetEntryProject timesheetEntryProject;
	private TimesheetEntryProjectDto timesheetEntryProjectDto;
	private TimesheetEntry timesheetEntry;
	private Timesheet timesheet;
	private Project project;
	private User user;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

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
	void TimesheetEntryProjectController_CreateTimesheetEntryProject_ReturnCreated() throws Exception {
		when(timesheetEntryProjectService.create(any(TimesheetEntryProjectDto.class))).thenReturn(timesheetEntryProjectDto);
		
		ResultActions response = mockMvc.perform(post("/timesheet-entry-project")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(timesheetEntryProjectDto)));
		
		response.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", CoreMatchers.is(timesheetEntryProjectDto.getId().intValue())))
				.andExpect(jsonPath("$.timesheetEntry.timesheet.user.username", CoreMatchers.is(timesheetEntryProjectDto.getTimesheetEntry()
						.getTimesheet().getUser().getUsername())));
	}
	
	
}
