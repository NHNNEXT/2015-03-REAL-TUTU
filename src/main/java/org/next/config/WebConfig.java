package org.next.config;

import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
public class WebConfig {

    private final Long DEFAULT_FILE_MAX_SUPPORT_FILE_SIZE = (long) (1024 *1024 * 2);	// 2MB
    private final Long DEFAULT_FILE_MAX_SUPPORT_FILE_SIZE_PER_REQUEST = (long) (1024 *1024 * 6);	// 6MB

    @Bean
    public Filter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public Filter httpPutFormContentFilter() {
        return new HttpPutFormContentFilter();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DEFAULT_FILE_MAX_SUPPORT_FILE_SIZE);
        factory.setMaxRequestSize(DEFAULT_FILE_MAX_SUPPORT_FILE_SIZE_PER_REQUEST);

        return factory.createMultipartConfig();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
