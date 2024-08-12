package com.timechaser.mapper;

import com.timechaser.dto.TimesheetEntryDto;
import com.timechaser.entity.TimesheetEntry;

public class TimesheetEntryMapper {
	public static TimesheetEntry toEntity(TimesheetEntryDto timesheetEntryDto) {
		return TimesheetEntry.builder()
				.id(timesheetEntryDto.getId())
				.date(timesheetEntryDto.getDate())
				.hoursWorked(timesheetEntryDto.getHoursWorked())
				.project(timesheetEntryDto.getProject())
				.timesheet(timesheetEntryDto.getTimesheet())
				.build();
	}
	
	public static TimesheetEntryDto toDto(TimesheetEntry timesheetEntry) {
		return TimesheetEntryDto.builder()
				.id(timesheetEntry.getId())
				.date(timesheetEntry.getDate())
				.hoursWorked(timesheetEntry.getHoursWorked())
				.project(timesheetEntry.getProject())
				.timesheet(timesheetEntry.getTimesheet())
				.build();
	}
}
