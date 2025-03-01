package com.timechaser.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.timechaser.dto.TimesheetEntryDto;
import com.timechaser.entity.TimesheetEntry;
import com.timechaser.exception.CreateException;
import com.timechaser.mapper.TimesheetEntryMapper;
import com.timechaser.repository.TimesheetEntryRepository;

@Service
public class TimesheetEntryService {
	private final Logger logger = LoggerFactory.getLogger(TimesheetEntryService.class);
	
	private final TimesheetEntryRepository timesheetEntryRepository;
	
	public TimesheetEntryService(TimesheetEntryRepository timesheetEntryRepository) {
		this.timesheetEntryRepository = timesheetEntryRepository;
	}
	
	@Transactional
	public TimesheetEntryDto create(TimesheetEntryDto timesheetEntryDto) {
		logger.info("Saving timesheet entry");
		
		Long id = null;
		try {
			TimesheetEntry timesheetEntry = TimesheetEntryMapper.toEntity(timesheetEntryDto);
			timesheetEntry = timesheetEntryRepository.save(timesheetEntry);
			
			id = timesheetEntry.getId();
			logger.info("Saved timesheet entry with id {}", id);
			return TimesheetEntryMapper.toDto(timesheetEntry);
		} catch (Exception e) {			
			logger.error("Error occured while creating timesheet entry with id {}", id);
			throw new CreateException("Failed to create timesheet entry", e);
		}
	}
	
	public Optional<TimesheetEntry> findById(Long id) {
	    	logger.info("Attempting to retrieve timesheet entry with id: {}", id);
	    	return timesheetEntryRepository.findById(id);
	    }
	 
	@Transactional
    public void deleteById(Long id) {
		logger.info("Deleting Timesheet Entry with ID: {}", id);
		
        timesheetEntryRepository.deleteById(id);
    }
}
