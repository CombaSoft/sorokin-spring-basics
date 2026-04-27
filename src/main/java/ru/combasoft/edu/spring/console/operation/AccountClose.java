package ru.combasoft.edu.spring.console.operation;

import org.springframework.stereotype.Component;
import ru.combasoft.edu.spring.console.CliOperationType;
import ru.combasoft.edu.spring.model.Account;
import ru.combasoft.edu.spring.service.AccountService;
import ru.combasoft.edu.spring.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AccountClose implements CliOperationHandler {

    private final AccountService accountService;
    private final UserService userService;

    public AccountClose(AccountService accountService,
                        UserService userService) {

        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void handle(Scanner sc) {

        int accountIdToClose = CliUtils.getInt(sc, "account id to close");
        Account accountToClose = accountService.getById(accountIdToClose);

        int userId = accountToClose.getUserId();

        Set<Account> userAccounts = accountService.getByUserId(userId);

        Optional<Account> accountWithMinIdOptional = userAccounts.stream()
                .filter(a -> a.getId() != accountToClose.getId())
                .min(Comparator.comparingInt(Account::getId));

        if (accountWithMinIdOptional.isPresent()) {

            Account accountWithMinId = accountWithMinIdOptional.get();
            int balance = accountToClose.getMoneyAmount();

            List<Account> accountsToSet = userAccounts.stream()
                    .filter(a -> a.getId() != accountToClose.getId())
                    .collect(Collectors.toList());

            userService.getById(userId).getAccountList().clear();
            userService.getById(userId).getAccountList().addAll(accountsToSet);

            accountService.delete(accountIdToClose);

            accountWithMinId.setMoneyAmount(accountWithMinId.getMoneyAmount() + balance);

            System.out.printf("Account %d closed. Remaining balance %d transferred to account %d.",
                    accountIdToClose, balance, accountWithMinId.getId());
        } else {
            throw new IllegalArgumentException(String
                    .format("Can't close account %d because it is a only one for user %d",
                            accountIdToClose, accountToClose.getUserId()));
        }
    }

    @Override
    public CliOperationType getCliCommandType() {
        return CliOperationType.ACCOUNT_CLOSE;
    }
}
