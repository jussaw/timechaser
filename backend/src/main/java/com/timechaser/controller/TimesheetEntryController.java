package com.timechaser.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timechaser.dto.TimesheetEntryDto;
import com.timechaser.entity.TimesheetEntry;
import com.timechaser.exception.NotFoundException;
import com.timechaser.mapper.TimesheetEntryMapper;
import com.timechaser.service.TimesheetEntryService;

@RestController
@RequestMapping("/timesheet-entry")
public class TimesheetEntryController {
	private final Logger logger = LoggerFactory.getLogger(TimesheetEntryController.class);
	private final TimesheetEntryService timesheetEntryService;
	
	public TimesheetEntryController(TimesheetEntryService timesheetEntryService) {
		this.timesheetEntryService = timesheetEntryService;
	}
	
	@PreAuthorize("hasRole(T(com.timechaser.enums.UserRoles).EMPLOYEE) " + 
	"|| hasRole(T(com.timechaser.enums.UserRoles).MANAGER)")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TimesheetEntryDto> createTimesheetEntry(@Valid @RequestBody TimesheetEntryDto timesheetEntryDto){
		logger.info("Received request to create timesheet entry with id {}", timesheetEntryDto.getId());
		
		timesheetEntryDto = timesheetEntryService.create(timesheetEntryDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(timesheetEntryDto);
	}

	@PreAuthorize("hasRole(T(com.timechaser.enums.UserRoles).EMPLOYEE) " + 
			"|| hasRole(T(com.timechaser.enums.UserRoles).MANAGER)")
	@GetMapping("/{id}")
	public ResponseEntity<TimesheetEntryDto> findTimesheetEntryById(@PathVariable Long id) {
		logger.info("Received request to get timesheet entry by id: {}", id);
		
		Optional<TimesheetEntry> timesheetEntryOptional = timesheetEntryService.findById(id);
		
		TimesheetEntryDto response = timesheetEntryOptional
				.map(TimesheetEntryMapper::toDto)
				.orElseThrow(() -> new NotFoundException("Timesheet entry not found with id: " + id));
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PreAuthorize("hasRole(T(com.timechaser.enums.UserRoles).EMPLOYEE) " + 
			"|| hasRole(T(com.timechaser.enums.UserRoles).MANAGER)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTimesheetEntry(@PathVariable("id") Long id) {
        logger.info("Received request to delete project with ID {}", id);

        timesheetEntryService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
	    
}
