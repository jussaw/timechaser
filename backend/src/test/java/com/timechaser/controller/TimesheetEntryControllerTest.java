package com.timechaser.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timechaser.dto.TimesheetEntryDto;
import com.timechaser.entity.Project;
import com.timechaser.entity.Timesheet;
import com.timechaser.entity.TimesheetEntry;
import com.timechaser.entity.User;
import com.timechaser.enums.UserRoles;
import com.timechaser.mapper.TimesheetEntryMapper;
import com.timechaser.service.TimesheetEntryService;

@ContextConfiguration
@SpringBootTest
@WithMockUser(authorities = { UserRoles.EMPLOYEE, UserRoles.EMPLOYEE })
public class TimesheetEntryControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private TimesheetEntryService timesheetEntryService;
	
	private TimesheetEntry timesheetEntry;
	private Project project1, project2;
	private TimesheetEntryDto timesheetEntryDto;
	private User user;
	private Timesheet timesheet;
	
	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
		user = new User();
		user.setUsername("testuser");
		user.setPassword("password");
		user.setFirstName("First");
		user.setLastName("Last");
		
		project1 = new Project();
		project1.setId(1L);
		project1.setName("prj1");

		project2 = new Project();
		project2.setId(2L);
		project2.setName("prj2");

		timesheet = new Timesheet();
		timesheet.setUser(user);
		timesheet.setYear(2024);
		timesheet.setWeekNumber(20);
		timesheet.setTotalHours(new BigDecimal(40));
		
		timesheetEntry = new TimesheetEntry();
		timesheetEntry.setTimesheet(timesheet);
		timesheetEntry.setDate(LocalDate.of(2024, 8, 1));
		timesheetEntry.setHoursWorked(new BigDecimal(8));
		timesheetEntry.setId(1L);

		ArrayList<Project> projects = new ArrayList<>();
		projects.add(project1);
		projects.add(project2);
		timesheetEntry.setProjects(projects);
		
		timesheetEntryDto = TimesheetEntryMapper.toDto(timesheetEntry);
	}
	
	@Test 
	void TimesheetEntryController_CreateTimesheetEntry_ReturnCreated() throws Exception {
		when(timesheetEntryService.create(any(TimesheetEntryDto.class))).thenReturn(timesheetEntryDto);
		
		ResultActions response = mockMvc.perform(post("/timesheet-entry")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(timesheetEntryDto)));

		String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(timesheetEntryDto);
		System.out.println(json);
		
		response.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(timesheetEntry.getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.date", CoreMatchers.is(timesheetEntry.getDate().toString())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.hoursWorked", CoreMatchers.is(timesheetEntry.getHoursWorked().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.projects[0].id", CoreMatchers.is(timesheetEntry.getProjects().get(0).getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.projects[0].name", CoreMatchers.is(timesheetEntry.getProjects().get(0).getName().toString())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.projects[1].id", CoreMatchers.is(timesheetEntry.getProjects().get(1).getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.projects[1].name", CoreMatchers.is(timesheetEntry.getProjects().get(1).getName().toString())));
	}
	
	@Test 
	void TimesheetEntryController_GetTimesheetEntry_Success() throws Exception {
		when(timesheetEntryService.findById(1L)).thenReturn(Optional.of(timesheetEntry));
		
		ResultActions response = mockMvc.perform(get("/timesheet-entry/1")
	            .contentType(MediaType.APPLICATION_JSON));
		
		
		response.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(timesheetEntry.getId().intValue())));
	}
	
	@Test 
	void TimesheetEntryController_GetTimesheetEntry_NotFound() throws Exception {
		when(timesheetEntryService.findById(1L)).thenReturn(Optional.empty());
		
		ResultActions response = mockMvc.perform(get("/timesheet-entry/1")
	            .contentType(MediaType.APPLICATION_JSON));
		
		response.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
