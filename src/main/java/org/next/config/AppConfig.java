package org.next.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@Import({ApplicationSecurityConfig.class, PersistenceConfig.class})
@ComponentScan(
        basePackages = {
                "org.next",
        },
        excludeFilters = {
                @Filter(type = FilterType.ANNOTATION, value = Configuration.class)
        }
)
public class AppConfig {
}
