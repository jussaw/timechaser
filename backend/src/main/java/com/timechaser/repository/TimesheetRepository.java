package com.timechaser.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.timechaser.entity.Timesheet;

public interface TimesheetRepository extends JpaRepository<Timesheet, Long>{
	
	public Optional<Timesheet> findById(Long id);
	
}
