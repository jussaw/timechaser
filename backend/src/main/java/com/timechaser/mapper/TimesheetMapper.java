package com.timechaser.mapper;

import com.timechaser.dto.TimesheetDto;
import com.timechaser.entity.Timesheet;
import com.timechaser.entity.User;

public class TimesheetMapper {
    public static Timesheet toEntity(TimesheetDto timesheetDto) {
        return Timesheet.builder()
            .id(timesheetDto.getId())
            .user(timesheetDto.getUser())
            .year(timesheetDto.getYear())
            .weekNumber(timesheetDto.getWeekNumber())
            .status(timesheetDto.getStatus())
            .build();
    }

    public static TimesheetDto toDto(Timesheet timesheet) {
        return TimesheetDto.builder()
            .id(timesheet.getId())
            .user(new User(timesheet.getUser().getId()))
            .year(timesheet.getYear())
            .weekNumber(timesheet.getWeekNumber())
            .status(timesheet.getStatus())
            .build();
    }
}
