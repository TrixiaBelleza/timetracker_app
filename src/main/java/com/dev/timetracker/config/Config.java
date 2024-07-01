package com.dev.timetracker.config;

import com.dev.timetracker.entity.Project;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public Project[] projects() {
        return Project.values();
    }
}
