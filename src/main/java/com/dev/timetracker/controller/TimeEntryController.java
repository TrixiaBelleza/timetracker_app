package com.dev.timetracker.controller;

import com.dev.timetracker.entity.Project;
import com.dev.timetracker.entity.TimeEntry;
import com.dev.timetracker.entity.User;
import com.dev.timetracker.service.TimeEntryService;
import com.dev.timetracker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class TimeEntryController {
    private final Logger logger = LoggerFactory.getLogger(TimeEntryController.class);

    @Autowired
    private TimeEntryService timeEntryService;

    @Autowired
    private UserService userService;

    /**
     * Shows time entries by invoking the "/entries" route endpoint.
     *
     * @param userDetails The userDetails of the user that is logged in.
     * @param weeklyPage For pagination purposes - this is the current page for the weekly entries.
     * @param totalHrsPage For pagination purposes - this is the current page for the total hrs spent on a project.
     * @param model Model attribute in which the UI will be accessing from.
     * @return The page to be rendered.
     */
    @GetMapping("/entries")
    public String showTimeEntries(@AuthenticationPrincipal UserDetails userDetails,
                                  @RequestParam(defaultValue = "0") int weeklyPage,
                                  @RequestParam(defaultValue = "0") int totalHrsPage,
                                  Model model) {
        try {
            User user = userService.findUserByEmail(userDetails.getUsername());

            // Pagination for weekly time entries
            Pageable weeklyPageable = PageRequest.of(weeklyPage, 5); // 5 Entries per page
            Page<TimeEntry> timeEntriesPage = timeEntryService.getWeeklyTimeEntries(user.getId(), weeklyPageable);
            model.addAttribute("timeEntriesPage", timeEntriesPage);
            model.addAttribute("currentPage", weeklyPage);
            model.addAttribute("weeklyTotalPages", timeEntriesPage.getTotalPages());
            model.addAttribute("timeEntry", new TimeEntry());

            // Pagination for total hours per project
            Pageable totalHrsPageable = PageRequest.of(totalHrsPage, 5); // 5 Entries per page
            Page<Object[]> totalHoursPerProject = timeEntryService.getTotalHoursPerProject(user.getId(), totalHrsPageable);
            model.addAttribute("totalHoursPerProject", totalHoursPerProject);
            model.addAttribute("totalHrsCurrentPage", totalHrsPage);
            model.addAttribute("totalHrsTotalPages", totalHoursPerProject.getTotalPages());
        } catch (Exception e) {
            logger.warn("An error occurred while fetching time entries due to: " + e.getMessage(), e);
            model.addAttribute("errorMessage", "An error occurred while fetching time entries due to: " + e.getMessage());
        }
        return "entries";
    }

    /**
     * Adds time entry to TimeEntry table
     * @param hours Number of hours spent on a task
     * @param taskDescription Task description
     * @param project Project that the user has worked on
     * @param userDetails User details of the specific user that is logged in.
     * @param model Model attribute in which the UI will be accessing from.
     * @return The page to be rendered.
     */
    @PostMapping("/add-time-entry")
    public String addTimeEntry(@RequestParam int hours,
                               @RequestParam String taskDescription,
                               @RequestParam Project project,
                               @AuthenticationPrincipal UserDetails userDetails,
                               Model model) {
        try {
            User user = userService.findUserByEmail(userDetails.getUsername());
            timeEntryService.addTimeEntry(hours, taskDescription, project, user);
            return "redirect:/entries";
        } catch (Exception e) {
            logger.warn("An error occurred while adding the time entry due to: " + e.getMessage(), e);
            model.addAttribute("errorMessage", "An error occurred while adding the time entry due to: " + e.getMessage());
            return "entries"; // Display the entries page with error
        }
    }
}
