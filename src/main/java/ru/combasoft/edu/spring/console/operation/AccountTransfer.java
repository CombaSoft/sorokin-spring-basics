package ru.combasoft.edu.spring.console.operation;

import org.springframework.stereotype.Component;
import ru.combasoft.edu.spring.console.CliOperationType;
import ru.combasoft.edu.spring.service.AccountService;

import java.util.Scanner;

@Component
public class AccountTransfer implements CliOperationHandler {

    private final AccountService accountService;

    public AccountTransfer(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void handle(Scanner sc) {

        int accountIdFrom = CliUtils.getInt(sc, "source account id");
        int accountIdTo = CliUtils.getInt(sc, "target account id");
        int amount = CliUtils.getInt(sc, "amount");

        if (amount < 1) {
            throw new IllegalArgumentException("Amount should be 1 or greater");
        }

        int bankFee = accountService.transfer(accountIdFrom, accountIdTo, amount);

        System.out.printf("Transfer completed from account %d to account %d. " +
                "Amount: %d, commission: %d, recipient received: %d",
                accountIdFrom, accountIdTo, amount, bankFee, amount - bankFee);
    }

    @Override
    public CliOperationType getCliCommandType() {
        return CliOperationType.ACCOUNT_TRANSFER;
    }
}
