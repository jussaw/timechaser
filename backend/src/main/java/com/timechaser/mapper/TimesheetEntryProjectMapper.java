package com.timechaser.mapper;

import com.timechaser.dto.TimesheetEntryProjectDto;
import com.timechaser.entity.TimesheetEntryProject;

public class TimesheetEntryProjectMapper {
	public static TimesheetEntryProject toEntity(TimesheetEntryProjectDto timesheetEntryProjectDto) {
		return TimesheetEntryProject.builder()
				.id(timesheetEntryProjectDto.getId())
				.hours(timesheetEntryProjectDto.getHours())
				.project(timesheetEntryProjectDto.getProject())
				.timesheetEntry(timesheetEntryProjectDto.getTimesheetEntry())
				.build();		
	}
	
	public static TimesheetEntryProjectDto toDto(TimesheetEntryProject timesheetEntryProject) {
		return TimesheetEntryProjectDto.builder()
				.id(timesheetEntryProject.getId())
				.hours(timesheetEntryProject.getHours())
				.project(timesheetEntryProject.getProject())
				.timesheetEntry(timesheetEntryProject.getTimesheetEntry())
				.build();
	}
}
