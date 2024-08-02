package com.timechaser.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.timechaser.entity.Project;
import com.timechaser.entity.TimesheetEntry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimesheetEntryProjectDto {
	private Long id;
	@NotNull
	private Project project;
	@NotNull
	private TimesheetEntry timesheetEntry;
	@NotNull(message = "Hours is a required field")
	private BigDecimal hours; 
}
