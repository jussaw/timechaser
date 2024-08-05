package com.timechaser.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimesheetEntryProject {
	@Id
	@GeneratedValue(strategy  = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="timesheet_entry_id")
	private TimesheetEntry timesheetEntry;
	
	@ManyToOne
	@JoinColumn(name="project_id")
	private Project project;
	
	@Column(scale = 2)
	private BigDecimal hours;
	
}
