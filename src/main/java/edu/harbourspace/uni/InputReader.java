package edu.harbourspace.uni;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputReader {
    private final List<String> input = new ArrayList<>();

    public InputReader() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if ("FINISH".equals(line)) {
                break;
            } else {
                input.add(line);
            }
        }
    }

    public List<String> getInput() {
        return input;
    }
}


