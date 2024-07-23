package com.timechaser.mapper;

import com.timechaser.dto.TimesheetDto;
import com.timechaser.entity.Timesheet;

public class TimesheetMapper {
    public static Timesheet toEntity(TimesheetDto timesheetDto) {
        return Timesheet.builder()
            .id(timesheetDto.getId())
            .user(timesheetDto.getUser())
            .year(timesheetDto.getYear())
            .weekNumber(timesheetDto.getWeekNumber())
            .totalHours(timesheetDto.getTotalHours())
            .status(timesheetDto.getStatus())
            .build();
    }

    public static TimesheetDto toDto(Timesheet timesheet) {
        return TimesheetDto.builder()
            .id(timesheet.getId())
            .user(timesheet.getUser())
            .year(timesheet.getYear())
            .weekNumber(timesheet.getWeekNumber())
            .totalHours(timesheet.getTotalHours())
            .status(timesheet.getStatus())
            .build();
    }
}
