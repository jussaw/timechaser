package com.timechaser.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.timechaser.dto.TimesheetDto;
import com.timechaser.entity.Timesheet;
import com.timechaser.exception.NotFoundException;
import com.timechaser.mapper.TimesheetMapper;
import com.timechaser.service.TimesheetService;

@RestController
@RequestMapping("/timesheet")
public class TimesheetController {
    Logger logger = LoggerFactory.getLogger(TimesheetController.class);

    private final TimesheetService timesheetService;

    public TimesheetController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    @PreAuthorize("hasRole(T(com.timechaser.enums.UserRoles).EMPLOYEE)" +
            " || hasRole(T(com.timechaser.enums.UserRoles).MANAGER)")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimesheetDto> createTimesheet(@Valid @RequestBody TimesheetDto timesheetDto) {
        logger.info("Received request to create timesheet");

        timesheetDto = timesheetService.create(timesheetDto);

        logger.info("Created timesheet with id {}", timesheetDto.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(timesheetDto);
    }

    @PreAuthorize("hasRole(T(com.timechaser.enums.UserRoles).EMPLOYEE)" +
            " || hasRole(T(com.timechaser.enums.UserRoles).MANAGER)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTimesheet(@PathVariable("id") Long id) {
        logger.info("Received request to delete timesheet with ID {}", id);

        timesheetService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @PreAuthorize("hasRole(T(com.timechaser.enums.UserRoles).EMPLOYEE)" +
            " || hasRole(T(com.timechaser.enums.UserRoles).MANAGER)")
    @GetMapping("/{id}")    
    public ResponseEntity<TimesheetDto> findById(@PathVariable Long id) {
        logger.info("Received request to get timesheet with ID {}", id);

        Optional<Timesheet> timesheetOptional = timesheetService.findById(id);

        TimesheetDto response = timesheetOptional
                .map(TimesheetMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Timesheet not found with ID: " + id));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
