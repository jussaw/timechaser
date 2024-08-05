package com.timechaser.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.timechaser.dto.TimesheetEntryProjectDto;
import com.timechaser.entity.TimesheetEntryProject;
import com.timechaser.exception.CreateException;
import com.timechaser.mapper.TimesheetEntryProjectMapper;
import com.timechaser.repository.TimesheetEntryProjectRespository;

@Service
public class TimesheetEntryProjectService {
	Logger logger = LoggerFactory.getLogger(TimesheetEntryProjectService.class);
	
	private final TimesheetEntryProjectRespository timesheetEntryProjectRespository;
	
	public TimesheetEntryProjectService(TimesheetEntryProjectRespository timesheetEntryProjectRespository) {
		this.timesheetEntryProjectRespository = timesheetEntryProjectRespository;
	}
	
	@Transactional
	public TimesheetEntryProjectDto create(TimesheetEntryProjectDto timesheetEntryProjectDto) {
		logger.info("Creating timesheet entry with timesheet entry id {} and project id {}", 
				timesheetEntryProjectDto.getTimesheetEntry().getId(), 
				timesheetEntryProjectDto.getProject().getId());
	
		try {
			TimesheetEntryProject timesheetEntryProject = TimesheetEntryProjectMapper.toEntity(timesheetEntryProjectDto);
			timesheetEntryProject = timesheetEntryProjectRespository.save(timesheetEntryProject);
			
			return TimesheetEntryProjectMapper.toDto(timesheetEntryProject);
		} catch (Exception e) {
			logger.error("Error occured while creating timesheet entry project with timesheet entry id {} and project id {}", 
					timesheetEntryProjectDto.getTimesheetEntry().getId(), 
					timesheetEntryProjectDto.getProject().getId());
            throw new CreateException("Failed to create timesheet entry project", e);
		}
	}
}
