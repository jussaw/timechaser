package com.timechaser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timechaser.entity.Timesheet;

public interface TimesheetRepository extends JpaRepository<Timesheet, Long>{

}
