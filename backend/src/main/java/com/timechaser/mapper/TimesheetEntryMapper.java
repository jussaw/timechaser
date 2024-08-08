package com.timechaser.mapper;

import com.timechaser.dto.TimesheetEntryDto;
import com.timechaser.entity.TimesheetEntry;

public class TimesheetEntryMapper {
	public static TimesheetEntry toEntity(TimesheetEntryDto timesheetEntryDto) {
		return TimesheetEntry.builder()
				.id(timesheetEntryDto.getId())
				.projects(timesheetEntryDto.getProjects())
				.date(timesheetEntryDto.getDate())
				.hoursWorked(timesheetEntryDto.getHoursWorked())
				.build();
	}
	
	public static TimesheetEntryDto toDto(TimesheetEntry timesheetEntry) {
		return TimesheetEntryDto.builder()
				.id(timesheetEntry.getId())
				.projects(timesheetEntry.getProjects())
				.date(timesheetEntry.getDate())
				.hoursWorked(timesheetEntry.getHoursWorked())
				.build();
	}
}
