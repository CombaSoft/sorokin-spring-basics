package ru.combasoft.edu.spring.console.operation;

import ru.combasoft.edu.spring.console.CliOperationType;

import java.util.Scanner;

public interface CliOperationHandler {

    void handle(Scanner sc);
    CliOperationType getCliCommandType();
}
