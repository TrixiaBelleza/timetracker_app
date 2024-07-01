package com.dev.timetracker.service;

import com.dev.timetracker.entity.Project;
import com.dev.timetracker.entity.TimeEntry;
import com.dev.timetracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TimeEntryService {
    TimeEntry addTimeEntry(int hours, String taskDescription, Project project, User user);
    Page<TimeEntry> getWeeklyTimeEntries(Long userId, Pageable pageable);

    Page<Object[]> getTotalHoursPerProject(Long userId, Pageable pageable);
}
