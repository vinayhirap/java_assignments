package com.aurionpro.pig;

import java.util.Scanner;

public class UserInput {
    private Scanner scanner;

    public UserInput() {
        this.scanner = new Scanner(System.in);
    }

    public String readString(String message) {
        System.out.println(message);
        return scanner.next();
    }

    public int readInt(String message) {
        System.out.println(message);
        return scanner.nextInt();
    }
}
