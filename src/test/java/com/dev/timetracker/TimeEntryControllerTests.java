package com.dev.timetracker;
import com.dev.timetracker.controller.TimeEntryController;
import com.dev.timetracker.entity.Project;
import com.dev.timetracker.entity.TimeEntry;
import com.dev.timetracker.entity.User;
import com.dev.timetracker.service.TimeEntryService;
import com.dev.timetracker.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TimeEntryControllerTests {

    @Mock
    private TimeEntryService timeEntryService;

    @Mock
    private UserService userService;

    @Mock
    private UserDetails userDetails;

    @Mock
    private Model model;

    @InjectMocks
    private TimeEntryController timeEntryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTimeEntry() {
        User mockUser = new User();
        mockUser.setId(1L);
        when(userService.findUserByEmail(any())).thenReturn(mockUser);

        String result = timeEntryController.addTimeEntry(2, "Task description", Project.PROJECT_A, userDetails, model);

        verify(timeEntryService, times(1)).addTimeEntry(2, "Task description", Project.PROJECT_A, mockUser);
        verify(model, never()).addAttribute(eq("errorMessage"), anyString());
        assert(result.equals("redirect:/entries"));
    }

    @Test
    void testShowTimeEntries() {
        User mockUser = new User();
        mockUser.setId(1L);

        // Mocking Time Entries
        List<TimeEntry> mockEntries = Collections.singletonList(new TimeEntry());
        Page<TimeEntry> mockEntriesPage = new PageImpl<>(mockEntries, PageRequest.of(0, 5), 1);

        // Mocking Total Hours Per Project
        List<Object[]> mockTotalHours = Collections.singletonList(new Object[]{"Project1", 10});
        Page<Object[]> mockTotalHoursPage = new PageImpl<>(mockTotalHours, PageRequest.of(0, 5), 1);

        when(userService.findUserByEmail(any())).thenReturn(mockUser);
        when(timeEntryService.getWeeklyTimeEntries(anyLong(), any(Pageable.class))).thenReturn(mockEntriesPage);
        when(timeEntryService.getTotalHoursPerProject(anyLong(), any(Pageable.class))).thenReturn(mockTotalHoursPage);

        String viewName = timeEntryController.showTimeEntries(userDetails, 0, 0, model);

        verify(model, times(1)).addAttribute(eq("timeEntriesPage"), eq(mockEntriesPage));
        verify(model, times(1)).addAttribute(eq("currentPage"), eq(0));
        verify(model, times(1)).addAttribute(eq("weeklyTotalPages"), eq(mockEntriesPage.getTotalPages()));

        verify(model, times(1)).addAttribute(eq("totalHoursPerProject"), eq(mockTotalHoursPage));
        verify(model, times(1)).addAttribute(eq("totalHrsCurrentPage"), eq(0));
        verify(model, times(1)).addAttribute(eq("totalHrsTotalPages"), eq(mockTotalHoursPage.getTotalPages()));

        assertEquals("entries", viewName);
    }
}
