package com.timechaser.mapper;

import com.timechaser.dto.TimesheetEntryDto;
import com.timechaser.entity.TimesheetEntry;

public class TimesheetEntryMapper {
	public static TimesheetEntry toEntity(TimesheetEntryDto timesheetEntryDto) {
		return TimesheetEntry.builder()
				.id(timesheetEntryDto.getId())
				.timesheet(timesheetEntryDto.getTimesheet())
				.date(timesheetEntryDto.getDate())
				.hoursWorked(timesheetEntryDto.getHoursWorked())
				.build();
	}
	
	public static TimesheetEntryDto toDto(TimesheetEntry timesheetEntry) {
		return TimesheetEntryDto.builder()
				.id(timesheetEntry.getId())
				.timesheet(timesheetEntry.getTimesheet())
				.date(timesheetEntry.getDate())
				.hoursWorked(timesheetEntry.getHoursWorked())
				.build();
	}
}
