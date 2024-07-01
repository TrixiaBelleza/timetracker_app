package com.dev.timetracker.repository;

import com.dev.timetracker.entity.TimeEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {
    List<TimeEntry> findByUserId(Long userId);

    @Query("SELECT t FROM TimeEntry t WHERE t.user.id = :userId AND t.timestamp >= :oneWeekAgo")
    Page<TimeEntry> findEntriesFromLastWeek(@Param("userId") Long userId, @Param("oneWeekAgo") LocalDateTime oneWeekAgo, Pageable pageable);

    @Query("SELECT t.project, SUM(t.hours) FROM TimeEntry t WHERE t.user.id = :userId GROUP BY t.project")
    Page<Object[]> findTotalHoursPerProject(@Param("userId") Long userId, Pageable pageable);
}
