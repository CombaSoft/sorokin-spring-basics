package ru.combasoft.edu.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.combasoft.edu.spring.config.AppConfig;
import ru.combasoft.edu.spring.console.ConsoleListener;

public class App {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ConsoleListener listener = context.getBean(ConsoleListener.class);
        listener.run();
    }
}