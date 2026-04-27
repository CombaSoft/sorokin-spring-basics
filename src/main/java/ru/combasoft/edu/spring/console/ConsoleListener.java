package ru.combasoft.edu.spring.console;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ru.combasoft.edu.spring.console.operation.CliOperationHandler;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ConsoleListener {

    private final Map<CliOperationType, CliOperationHandler> commandHandlers;
    private Set<String> availableCommandNames;

    public ConsoleListener(List<CliOperationHandler> operations) {

        if (operations == null || operations.isEmpty()) {
            throw new IllegalArgumentException("There is empty List of CLI Operations");
        }

        this.commandHandlers = new HashMap<>();
        operations.forEach(o ->
                commandHandlers.put(o.getCliCommandType(), o));

        if (commandHandlers.keySet().size() != operations.size()) {
            throw new IllegalArgumentException("There are duplicates in CLI Operations List");
        }
    }

    @PostConstruct
    private void init() {
        availableCommandNames = commandHandlers.keySet().stream()
                .map(k -> k.name().toUpperCase()).collect(Collectors.toSet());
        availableCommandNames.add(CliOperationType.EXIT.name().toUpperCase());
    }

    public void run() {

        Scanner sc = new Scanner(System.in);
        try {
            greetUser();
            runMainCycle(sc);
        } finally {
            sc.close();
        }
    }

    private void runMainCycle(Scanner sc) {
        while (true) {
            System.out.println("Enter command:");

            String userCommandName = sc.nextLine().toUpperCase();

            if (!availableCommandNames.contains(userCommandName)) {
                System.out.println("Unknown command: " + userCommandName);
                printAvailableCommandList();
                continue;
            }

            CliOperationType userCommand = CliOperationType.valueOf(userCommandName);

            if (CliOperationType.EXIT.equals(userCommand)) {
                System.out.println("MiniBank stopped.");
                break;
            }

            CliOperationHandler handler = commandHandlers.get(userCommand);

            try {
                handler.handle(sc);
                System.out.println("\n");
            } catch (Exception e) {
                System.out.printf("Error: %s \n", e.getMessage());
                System.out.println("Return to main menu. \n");
            }
        }
    }

    private void greetUser() {

        System.out.println("MiniBank started. Type EXIT to stop.");

        printAvailableCommandList();
    }

    private void printAvailableCommandList() {
        String commandList = availableCommandNames.stream()
                .collect(Collectors.joining(", "));

        System.out.println("Available commands: " + commandList + "\n");
    }
}
