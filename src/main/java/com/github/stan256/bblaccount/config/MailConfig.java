package com.github.stan256.bblaccount.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
@PropertySource("classpath:mail.properties")
@EnableAsync
public class MailConfig {
    @Bean
    @Primary
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/templates/");
        bean.setPreferFileSystemAccess(true);
        bean.setDefaultEncoding("UTF-8");
        return bean;
    }
}
