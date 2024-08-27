package com.timechaser.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.timechaser.enums.TimesheetStatus;
import com.timechaser.entity.User;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimesheetDto {
    private Long id;
    @NotNull(message = "User is a required field")
    private User user;
    @NotNull(message = "Year is a required field")
    private Integer year;
    @NotNull(message = "Week number is a required field")
    private Integer weekNumber;
    @NotNull(message = "Timesheet status is a required field")
    private TimesheetStatus status;
}
