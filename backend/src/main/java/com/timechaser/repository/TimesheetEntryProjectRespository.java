package com.timechaser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timechaser.entity.TimesheetEntryProject;
import com.timechaser.entity.TimesheetEntryProjectKey;

public interface TimesheetEntryProjectRespository extends JpaRepository<TimesheetEntryProject, TimesheetEntryProjectKey>{

}
