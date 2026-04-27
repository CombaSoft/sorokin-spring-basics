package ru.combasoft.edu.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("ru.combasoft.edu.spring.*")
@PropertySource("classpath:application.properties")
public class AppConfig {
}
