package ru.combasoft.edu.spring.service;

import org.springframework.stereotype.Service;
import ru.combasoft.edu.spring.config.AccountProperties;
import ru.combasoft.edu.spring.model.Account;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountProperties properties;
    private int idCounter = 0;
    private Map<Integer, Account> accountMap = new HashMap();
    private Map<Integer, Set<Account>> accountsByUser = new HashMap();

    public AccountService(AccountProperties properties) {
        this.properties = properties;
    }

    public Account create(int userId) {
        idCounter++;
        Account account = new Account(idCounter, userId, properties.getDefaultAmount());
        accountMap.put(idCounter, account);
        accountsByUser.computeIfAbsent(userId, HashSet::new).add(account);
        return account;
    }

    public boolean delete(int id) {

        Account account = getById(id);
        int userId = account.getUserId();

        accountMap.remove(id);

        Set<Account> accounts = accountsByUser.get(userId);
        accounts.remove(account);

        if(accounts.isEmpty()) {
            accountsByUser.remove(userId);
        }

        return true;
    }

    public Account getById(int id) {
        return Optional.ofNullable(accountMap.get(id)).orElseThrow(() ->
                new IllegalArgumentException(String.format("Account with id %d does not exist", id)));
    }

    public Set<Account> getByUserId(int userId) {
        if(!accountsByUser.keySet().contains(userId)) {
            throw new IllegalArgumentException(String.format("There are no accounts for user id ", userId));
        }
        return new HashSet<>(accountsByUser.get(userId));
    }

    public int transfer(int idFrom, int idTo, int amount) {

        Account accountFrom = getById(idFrom);

        Account accountTo = getById(idTo);

        if (amount > accountFrom.getMoneyAmount()) {
            throw new IllegalArgumentException(String.format("insufficient funds on account id=%d, " +
                    "moneyAmount=%d, attempted withdraw=%d",
                    idFrom, accountFrom.getMoneyAmount(), amount));
        }

        accountFrom.setMoneyAmount(accountFrom.getMoneyAmount() - amount);

        int userIdFrom = accountFrom.getUserId();
        int userIdTo = accountTo.getUserId();

        int bankFee = userIdFrom == userIdTo ?
                0 :
                (int)Math.round(properties.getTransferCommission() * amount);

        accountTo.setMoneyAmount(accountTo.getMoneyAmount() + amount - bankFee);

        return bankFee;
    }
}
