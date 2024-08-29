package com.oneune.informator.rest.configs;

import com.oneune.informator.rest.configs.properties.RussianMailIntegrationProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RussianMailIntegrationProperties.class)
@Log4j2
public class InformatorConfig {
}
