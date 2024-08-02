package com.timechaser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timechaser.entity.TimesheetEntryProject;

public interface TimesheetEntryProjectRespository extends JpaRepository<TimesheetEntryProject, Long>{

}
