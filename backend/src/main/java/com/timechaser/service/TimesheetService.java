package com.timechaser.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timechaser.dto.TimesheetDto;
import com.timechaser.entity.Project;
import com.timechaser.entity.Timesheet;
import com.timechaser.exception.CreateException;
import com.timechaser.mapper.TimesheetMapper;
import com.timechaser.repository.TimesheetRepository;

@Service
public class TimesheetService {
    Logger logger = LoggerFactory.getLogger(TimesheetService.class);

    private final TimesheetRepository timesheetRepository;

    public TimesheetService(TimesheetRepository timesheetRepository) {
        this.timesheetRepository = timesheetRepository;
    }

    @Transactional
    public TimesheetDto create(TimesheetDto timesheetDto) {
        logger.info("Saving timesheet to repository");

        Long id = null;
        try {
            Timesheet timesheet = TimesheetMapper.toEntity(timesheetDto);
            timesheet = timesheetRepository.save(timesheet);
            
            id = timesheetDto.getId();
            logger.info("Saved timesheet with id {}", id);            
            return TimesheetMapper.toDto(timesheet);
        } catch (Exception e) {
        	logger.error("Error occured while creating timesheet with id {}", id);
            throw new CreateException("Failed to create timesheet", e);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        logger.info("Deleting timesheet with id {}", id);

        timesheetRepository.deleteById(id);
    }
    
    public Optional<Timesheet> findById(Long id) {
        logger.info("Finding Timesheet with id: {}", id);

        Optional<Timesheet> timesheet = timesheetRepository.findById(id);
        
        return timesheet;
    }    
    
}
