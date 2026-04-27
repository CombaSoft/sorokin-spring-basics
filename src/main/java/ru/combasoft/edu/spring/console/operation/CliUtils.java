package ru.combasoft.edu.spring.console.operation;

import java.util.Scanner;

public class CliUtils {

    public static int getInt(Scanner sc, String meaning) {

        System.out.printf("Enter %s: ", meaning);
        String strId = sc.nextLine();

        int result;

        try {
            result = Integer.valueOf(strId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Entered %s is not a number", meaning));
        }
        return result;
    }
}
