package com.timechaser.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timechaser.dto.TimesheetEntryProjectDto;
import com.timechaser.service.TimesheetEntryProjectService;

@RestController
@RequestMapping("/timesheet-entry-project")
public class TimesheetEntryProjectController {
	private final Logger logger = LoggerFactory.getLogger(TimesheetEntryProjectController.class);
	private final TimesheetEntryProjectService timesheetEntryProjectService;
	
	public TimesheetEntryProjectController(TimesheetEntryProjectService timesheetEntryProjectService) {
		this.timesheetEntryProjectService = timesheetEntryProjectService;
	}
	@PreAuthorize("hasRole(T(com.timechaser.enums.UserRoles).EMPLOYEE) " + 
			"|| hasRole(T(com.timechaser.enums.UserRoles).MANAGER)")
			@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
			public ResponseEntity<TimesheetEntryProjectDto> createTimesheetEntryProject(@Valid @RequestBody TimesheetEntryProjectDto timesheetEntryProjectDto){
				logger.info("Received request to create timesheet entry project with timesheet entry id {} and project id {}", 
						timesheetEntryProjectDto.getTimesheetEntry().getId(), 
						timesheetEntryProjectDto.getProject().getId());
				
				timesheetEntryProjectDto = timesheetEntryProjectService.create(timesheetEntryProjectDto);
				
				return ResponseEntity.status(HttpStatus.CREATED).body(timesheetEntryProjectDto);
			}
}
