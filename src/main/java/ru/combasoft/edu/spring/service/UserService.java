package ru.combasoft.edu.spring.service;

import org.springframework.stereotype.Service;
import ru.combasoft.edu.spring.model.Account;
import ru.combasoft.edu.spring.model.User;

import java.util.*;

@Service
public class UserService {
    private int idCounter = 0;
    private Map<Integer, User> userMap = new HashMap();
    private Set<String> logins = new HashSet<>();

    private final AccountService accountService;

    public UserService(AccountService accountService) {
        this.accountService = accountService;
    }

    public User create(String login) {

        if(logins.contains(login)) {
            throw new IllegalArgumentException(String.format("User with login %s already exists.", login));
        }

        idCounter++;
        User user = new User(idCounter, login);
        Account account = accountService.create(idCounter);
        user.getAccountList().add(account);
        userMap.put(idCounter, user);
        logins.add(login);
        return user;
    }

    public User getById(int id) {
        return Optional.ofNullable(userMap.get(id)).orElseThrow(() ->
                new IllegalArgumentException(String.format("User with id %d does not exist", id)));
    }

    public List<User> getAll() {
        return userMap.values().stream().toList();
    }
}
