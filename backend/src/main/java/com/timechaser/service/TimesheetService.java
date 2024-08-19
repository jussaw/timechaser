package com.timechaser.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timechaser.dto.TimesheetDto;
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
        logger.info("Creating timesheet with id {}", timesheetDto.getId());

        try {
            Timesheet timesheet = TimesheetMapper.toEntity(timesheetDto);
            timesheet = timesheetRepository.save(timesheet);

            return TimesheetMapper.toDto(timesheet);
        } catch (Exception e) {
            logger.error("Error occured while creating timesheet with id {}", timesheetDto.getId());
            throw new CreateException("Failed to create timesheet", e);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        logger.info("Deleting timesheet with id {}", id);

        timesheetRepository.deleteById(id);
    }
}
