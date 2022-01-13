package com.volodymyrkozlov.filemanagement.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig {
    private static final String UNKNOWN = "UNKNOWN";

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of(UNKNOWN);
    }
}
