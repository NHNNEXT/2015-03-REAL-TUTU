package org.next.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class CacheConfig {

    @Autowired
    @Bean
    public CacheManager cacheManager(net.sf.ehcache.CacheManager cacheManager) {
        EhCacheCacheManager eccm = new EhCacheCacheManager();
        eccm.setCacheManager(cacheManager);
        return eccm;
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean ecmfb = new EhCacheManagerFactoryBean();
        ecmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
        ecmfb.setShared(true);
        return ecmfb;
    }
}