package com.timechaser.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;

@Data 
@Entity
public class TimesheetEntryProject {
	@EmbeddedId
	private TimesheetEntryProjectKey id;
	
	@ManyToOne
	@MapsId("timesheet_entry_id")
	@JoinColumn(name="timesheet_entry_id")
	private TimesheetEntry timesheetEntry;
	
	@ManyToOne
	@MapsId("project_id")
	@JoinColumn(name="project_id")
	private Project project;
	
	@Column(scale = 2)
	private BigDecimal hours;
	
}
