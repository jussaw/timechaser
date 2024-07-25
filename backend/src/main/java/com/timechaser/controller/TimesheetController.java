package com.timechaser.controller;

import javax.validation.Valid;

import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.timechaser.dto.TimesheetDto;
import com.timechaser.service.TimesheetService;

@RestController
@RequestMapping("/timesheet")
public class TimesheetController {
    Logger logger = LoggerFactory.getLogger(TimesheetController.class);

    private final TimesheetService timesheetService;

    public TimesheetController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    @PreAuthorize("hasRole(T(com.timechaser.enums.UserRoles).EMPLOYEE)")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimesheetDto> createTimesheet(@Valid @RequestBody TimesheetDto timesheetDto) {

        logger.info("Received request to create timesheet with id {}", timesheetDto.getId());

        timesheetDto = timesheetService.create(timesheetDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(timesheetDto);
    }
}
