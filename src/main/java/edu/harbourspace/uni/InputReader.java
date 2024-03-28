package edu.harbourspace.uni;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputReader {
    private final List<String> input = new ArrayList<>();
    private final int maxPosition;

    public InputReader() {
        System.out.println("Enter max-position: ");
        Scanner scanner = new Scanner(System.in);
        maxPosition = scanner.nextInt();
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if ("FINISH".equals(line)) {
                break;
            } else {
                input.add(line);
            }
        }
        scanner.close();

    }

    public List<String> readInput() {
        return input;
    }
    public int getMaxPosition() { return maxPosition; }
}


