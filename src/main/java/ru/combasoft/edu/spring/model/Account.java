package ru.combasoft.edu.spring.model;

import java.util.Objects;

public class Account {

    private final int id;
    private final int userId;
    private int moneyAmount;

    public Account(int id, int userId, int moneyAmount) {
        this.id = id;
        this.userId = id;
        this.moneyAmount = moneyAmount;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && userId == account.userId && moneyAmount == account.moneyAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, moneyAmount);
    }

    @Override
    public String toString() {
        return "Account {" +
                "id=" + id +
                ", userId=" + userId +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
