package edu.harbourspace.uni;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputReader {
    private final List<String> input;
    public InputReader(){
        Scanner scanner = new Scanner(System.in);
        input = new ArrayList<>();
        while (scanner.hasNextLine()){
            input.add(scanner.nextLine());
        }
        scanner.close();
    }

    public List<String> getInput() {
        return input;
    }
}
