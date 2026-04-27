package ru.combasoft.edu.spring.console.operation;

import org.springframework.stereotype.Component;
import ru.combasoft.edu.spring.console.CliOperationType;
import ru.combasoft.edu.spring.model.Account;
import ru.combasoft.edu.spring.service.AccountService;

import java.util.Scanner;

@Component
public class AccountDeposit implements CliOperationHandler {

    private final AccountService accountService;

    public AccountDeposit(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void handle(Scanner sc) {

        int accountId = CliUtils.getInt(sc, "account id");

        Account account = accountService.getById(accountId);

        int amount = CliUtils.getInt(sc, "amount");

        if (amount < 1) {
            throw new IllegalArgumentException("Amount should be 1 or greater");
        }

        account.setMoneyAmount(account.getMoneyAmount() + amount);

        System.out.printf("Deposited %d to account %d. New balance: %d",
                amount, accountId, account.getMoneyAmount());
    }

    @Override
    public CliOperationType getCliCommandType() {
        return CliOperationType.ACCOUNT_DEPOSIT;
    }
}
