package com.oneune.informator.rest.configs.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.time.Duration;

@ConfigurationProperties(prefix = "integration.russian-mail")
@PropertySource("classpath:application.yml")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class RussianMailIntegrationProperties extends AbstractIntegrationProperties {
    Duration interval;
}
