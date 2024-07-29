package com.timechaser.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.timechaser.dto.TimesheetDto;
import com.timechaser.entity.Timesheet;
import com.timechaser.entity.User;
import com.timechaser.enums.TimesheetStatus;
import com.timechaser.exception.CreateException;
import com.timechaser.mapper.TimesheetMapper;
import com.timechaser.repository.TimesheetRepository;

@ExtendWith(MockitoExtension.class)
public class TimesheetServiceTest {

    @Mock
    private TimesheetRepository timesheetRepository;

    @InjectMocks
    private TimesheetService timesheetService;

    private User user;
    private Timesheet timesheet;
    private TimesheetDto timesheetDto;

    @BeforeEach
    void setUp() {
		user = new User();
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setId(1L);
		user.setUsername("Test");

		timesheet = new Timesheet();
		timesheet.setId(1L);
		timesheet.setUser(user);
		timesheet.setYear(2024);
		timesheet.setWeekNumber(4);
		timesheet.setTotalHours(new BigDecimal("4"));
		timesheet.setStatus(TimesheetStatus.PENDING);

		timesheetDto = TimesheetMapper.toDto(timesheet);
    }

    @Test
    void TimesheetService_Create_Success() {
        when(timesheetRepository.save(any(Timesheet.class))).thenReturn(timesheet);

        TimesheetDto response = timesheetService.create(timesheetDto);

        assertNotNull(response);
        assertEquals(timesheet.getId(), response.getId());
        assertEquals(timesheet.getUser(), response.getUser());
        assertEquals(timesheet.getUser().getFirstName(), response.getUser().getFirstName());
        assertEquals(timesheet.getUser().getLastName(), response.getUser().getLastName());
        assertEquals(timesheet.getUser().getId(), response.getUser().getId());
        assertEquals(timesheet.getUser().getUsername(), response.getUser().getUsername());
        assertEquals(timesheet.getYear(), response.getYear());
        assertEquals(timesheet.getWeekNumber(), response.getWeekNumber());
        assertEquals(timesheet.getTotalHours(), response.getTotalHours());
        assertEquals(timesheet.getStatus(), response.getStatus());
        verify(timesheetRepository, times(1)).save(any(Timesheet.class));
    }

    @Test
    void TimesheetService_Create_Failure() {
        when(timesheetRepository.save(any(Timesheet.class))).thenThrow(new IllegalArgumentException());

        assertThatThrownBy(() -> timesheetService.create(timesheetDto))
            .isInstanceOf(CreateException.class);
    }
}
