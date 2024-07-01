package com.dev.timetracker.service.impl;

import com.dev.timetracker.entity.Project;
import com.dev.timetracker.entity.TimeEntry;
import com.dev.timetracker.entity.User;
import com.dev.timetracker.repository.TimeEntryRepository;
import com.dev.timetracker.service.TimeEntryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TimeEntryServiceImpl implements TimeEntryService {
    private final Logger logger = LoggerFactory.getLogger(TimeEntryServiceImpl.class);
    @Autowired
    private TimeEntryRepository timeEntryRepository;

    @Override
    public TimeEntry addTimeEntry(int hours, String taskDescription, Project project, User user) {
        try {
            TimeEntry timeEntry = new TimeEntry();
            timeEntry.setHours(hours);
            timeEntry.setTaskDescription(taskDescription);
            timeEntry.setProject(project);
            timeEntry.setTimestamp(LocalDateTime.now());
            timeEntry.setUser(user);

            logger.debug("Saving time entry for project: " + project.name() + " for user: " + user.getId());
            return timeEntryRepository.save(timeEntry);
        } catch (Exception e) {
            logger.warn("Failed to add time entry for project: " + project.name() + " for user: " + user.getId() + " due to: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Page<TimeEntry> getWeeklyTimeEntries(Long userId, Pageable pageable) {
        // For simplicity, assume we fetch entries for the last 7 days
        try {
            LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
            return timeEntryRepository.findEntriesFromLastWeek(userId, oneWeekAgo, pageable);
        } catch (Exception e) {
            logger.warn("Failed to get weekly time entries for user: " + userId + " due to: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Page<Object[]> getTotalHoursPerProject(Long userId, Pageable pageable) {
        try {
            return timeEntryRepository.findTotalHoursPerProject(userId, pageable);
        } catch (Exception e) {
            logger.warn("Failed to get total hours for project for user: " + userId + " due to: " + e.getMessage());
            return null;
        }
    }
}
