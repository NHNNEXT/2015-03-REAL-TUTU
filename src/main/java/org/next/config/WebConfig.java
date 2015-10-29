package org.next.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;

import javax.servlet.Filter;

@Configuration
public class WebConfig {

    @Bean
    public Filter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public Filter httpPutFormContentFilter() {
        return new HttpPutFormContentFilter();
    }

}
