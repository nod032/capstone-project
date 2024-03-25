package edu.harbourspace.uni;

import java.util.ArrayList;
import java.util.List;

public class StoringResult {
    private final List<String> executionMessages = new ArrayList<>();

    public void addExecutionMessage(String message) {
        executionMessages.add(message);
    }

    public void printExecutionMessages() {
        executionMessages.forEach(System.out::println);
    }
}
