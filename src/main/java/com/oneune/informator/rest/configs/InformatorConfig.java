package com.oneune.informator.rest.configs;

import com.oneune.informator.rest.configs.properties.DadataIntegrationProperties;
import com.oneune.informator.rest.configs.properties.RussianMailIntegrationProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties({
        RussianMailIntegrationProperties.class,
        DadataIntegrationProperties.class
})
@Log4j2
public class InformatorConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
