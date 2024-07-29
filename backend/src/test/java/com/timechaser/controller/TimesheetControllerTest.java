package com.timechaser.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

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
import com.timechaser.dto.TimesheetDto;
import com.timechaser.entity.Timesheet;
import com.timechaser.entity.User;
import com.timechaser.enums.TimesheetStatus;
import com.timechaser.enums.UserRoles;
import com.timechaser.mapper.TimesheetMapper;
import com.timechaser.service.TimesheetService;

@ContextConfiguration
@SpringBootTest
@WithMockUser(authorities = { UserRoles.EMPLOYEE })
public class TimesheetControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TimesheetService timesheetService;

	private User user;
	private Timesheet timesheet;
	private TimesheetDto timesheetDto;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		user = new User();
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setId(1L);
		user.setUsername("Test");

		timesheet = new Timesheet();
		timesheet.setId(1L);
		timesheet.setUser(user);
		timesheet.setYear(2024);
		timesheet.setWeekNumber(4);
		timesheet.setTotalHours(new BigDecimal("4"));
		timesheet.setStatus(TimesheetStatus.PENDING);

		timesheetDto = TimesheetMapper.toDto(timesheet);
	}

	@Test
    void TimesheetController_CreateTimesheet_ReturnCreated() throws Exception {
        when(timesheetService.create(any(TimesheetDto.class))).thenReturn(timesheetDto);

        ResultActions response = mockMvc.perform(post("/timesheet")
            .contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(timesheetDto)));

            response.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", CoreMatchers.is(timesheetDto.getId().intValue())))
                    .andExpect(jsonPath("$.user.firstName", CoreMatchers.is(timesheetDto.getUser().getFirstName())))
					.andExpect(jsonPath("$.user.lastName", CoreMatchers.is(timesheetDto.getUser().getLastName())))
					.andExpect(jsonPath("$.user.id", CoreMatchers.is(timesheetDto.getUser().getId().intValue())))
					.andExpect(jsonPath("$.user.username", CoreMatchers.is(timesheetDto.getUser().getUsername())))
					.andExpect(jsonPath("$.year", CoreMatchers.is(timesheetDto.getYear())))
					.andExpect(jsonPath("$.weekNumber", CoreMatchers.is(timesheetDto.getWeekNumber())))
					.andExpect(jsonPath("$.totalHours", CoreMatchers.is(timesheetDto.getTotalHours().intValue())))
					.andExpect(jsonPath("$.status", CoreMatchers.is(timesheetDto.getStatus().toString())));
    }
}
