package org.next.config;

import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;

@Import({ApplicationSecurityConfig.class, PersistenceConfig.class})
@ComponentScan(
        basePackages = {
                "org.next",
        },
        excludeFilters = {
                @Filter(type = FilterType.ANNOTATION, value = Configuration.class)
        }
)
@PropertySource("classpath:/config.properties")
public class AppConfig {
        public static final Integer pageSize = 10;
}