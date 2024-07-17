package com.timechaser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timechaser.entity.TimesheetEntry;

public interface TimesheetEntryRepository extends JpaRepository<TimesheetEntry, Long> {

}
