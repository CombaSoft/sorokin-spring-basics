package ru.combasoft.edu.spring.console.operation;

import org.springframework.stereotype.Component;
import ru.combasoft.edu.spring.console.CliOperationType;
import ru.combasoft.edu.spring.model.Account;
import ru.combasoft.edu.spring.model.User;
import ru.combasoft.edu.spring.service.AccountService;
import ru.combasoft.edu.spring.service.UserService;

import java.util.Scanner;

@Component
public class AccountCreate implements CliOperationHandler {

    private final AccountService accountService;
    private final UserService userService;

    public AccountCreate(AccountService accountService,
                         UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void handle(Scanner sc) {

        int userId = CliUtils.getInt(sc, "user id");

        User user = userService.getById(userId);

        Account account = accountService.create(userId);

        user.getAccountList().add(account);

        System.out.printf("Account created: %s", account);
    }

    @Override
    public CliOperationType getCliCommandType() {
        return CliOperationType.ACCOUNT_CREATE;
    }
}
