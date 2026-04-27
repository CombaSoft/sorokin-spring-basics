package ru.combasoft.edu.spring.console.operation;

import org.springframework.stereotype.Component;
import ru.combasoft.edu.spring.console.CliOperationType;
import ru.combasoft.edu.spring.model.User;
import ru.combasoft.edu.spring.service.UserService;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class ShowAllUsers implements CliOperationHandler {

    private final UserService userService;

    public ShowAllUsers(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handle(Scanner sc) {
        System.out.println("All users in system: \n");
        List<User> users = userService.getAll();

        if(users.isEmpty()) {
            System.out.println("There are no users in system yet\n");
            return;
        }
        String output = users.stream()
                .map(User::toString)
                .collect(Collectors.joining(", \n"));

        System.out.printf("%s", output);
    }

    @Override
    public CliOperationType getCliCommandType() {
        return CliOperationType.SHOW_ALL_USERS;
    }
}
