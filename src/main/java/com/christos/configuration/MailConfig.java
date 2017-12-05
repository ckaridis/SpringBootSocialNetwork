package com.christos.configuration;

// THIS is the configuration for the email

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

// SPEL = Spring Expression Language. We match the values with the values in the config file

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.port}")
    private Integer port;

    @Value("${mail.smtp.user}")
    private String user;

    @Value("${mail.smtp.pass}")
    private String password;

    // This is the class which is going to send the email
    @Bean
    public JavaMailSender mailSender() {

        // This is the implementation of the Java mail sender Interface
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(user);
        mailSender.setPassword(password);

        return mailSender;
    }

}
