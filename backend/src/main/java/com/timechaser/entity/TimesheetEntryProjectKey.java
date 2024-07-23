package com.timechaser.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class TimesheetEntryProjectKey implements Serializable {
	@Column(name = "timesheet_entry_id")
	private Long timesheetEntryId;
	
	@Column(name = "project_id")
	private Long projectId;
}
