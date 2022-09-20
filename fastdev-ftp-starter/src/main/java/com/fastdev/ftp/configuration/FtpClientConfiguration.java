package com.fastdev.ftp.configuration;

import com.fastdev.ftp.FtpClientTemplate;
import com.fastdev.ftp.config.FtpProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @className FtpClientConfiguration
 * @date 2022/8/6 14:13
 * @author zhouxi
 */
@Configuration
@EnableConfigurationProperties({FtpProperties.class})
public class FtpClientConfiguration {

    private final FtpProperties ftpProperties;

    public FtpClientConfiguration(FtpProperties ftpProperties) {
        this.ftpProperties = ftpProperties;
    }

    @Bean
    public FtpClientTemplate ftpClientTemplate() {
        return new FtpClientTemplate(ftpProperties);
    }
}
