package org.next.config;

import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;

import javax.servlet.FilterRegistration;

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