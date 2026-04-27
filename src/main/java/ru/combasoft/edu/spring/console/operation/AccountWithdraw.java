package ru.combasoft.edu.spring.console.operation;

import org.springframework.stereotype.Component;
import ru.combasoft.edu.spring.console.CliOperationType;
import ru.combasoft.edu.spring.model.Account;
import ru.combasoft.edu.spring.service.AccountService;

import java.util.Scanner;

@Component
public class AccountWithdraw implements CliOperationHandler {

    private final AccountService accountService;

    public AccountWithdraw(AccountService accountService) {
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

        if(amount > account.getMoneyAmount()) {
            throw new IllegalArgumentException(String
                    .format("insufficient funds on account id=%d, moneyAmount=%d, attempted withdraw=%d",
                            accountId, account.getMoneyAmount(), amount));
        }

        account.setMoneyAmount(account.getMoneyAmount() - amount);

        System.out.printf("Withdrawn amount %d from account %d. New balance: %d",
                amount, accountId, account.getMoneyAmount());
    }

    @Override
    public CliOperationType getCliCommandType() {
        return CliOperationType.ACCOUNT_WITHDRAW;
    }
}
