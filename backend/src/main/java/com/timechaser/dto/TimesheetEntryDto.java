package com.timechaser.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.timechaser.entity.Project;
import com.timechaser.entity.Timesheet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimesheetEntryDto {
	private Long id;
	@NotNull(message = "Date is a required field")
	private LocalDate date;
	@NotNull(message = "Hours worked is a required field")
	private BigDecimal hoursWorked;
	private List<Project> projects;
}
