package ru.combasoft.edu.spring.console.operation;

import org.springframework.stereotype.Component;
import ru.combasoft.edu.spring.console.CliOperationType;
import ru.combasoft.edu.spring.model.User;
import ru.combasoft.edu.spring.service.UserService;

import java.util.Scanner;

@Component
public class UserCreate implements CliOperationHandler {

    private final UserService userService;

    public UserCreate(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handle(Scanner sc) {
        System.out.println("Enter login:");
        String login = sc.nextLine();

        if(login == null || login.isEmpty()) {
            throw new IllegalArgumentException("Can`t create user with empty login.");
        }

        User user = userService.create(login);
        System.out.printf("User created: %s", user);
    }

    @Override
    public CliOperationType getCliCommandType() {
        return CliOperationType.USER_CREATE;
    }
}
