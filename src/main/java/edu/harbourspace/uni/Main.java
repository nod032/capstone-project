package edu.harbourspace.uni;

import edu.harbourspace.uni.inputParser.InputParser;

import java.util.Arrays;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        // TODO: pass max-position
        int maxPosition = parseMaxPosition(args);
        OrderProcessor orderProcessor = new OrderProcessor(maxPosition);
        InputReader inputReader = new InputReader();
        InputParser inputParser = new InputParser(orderProcessor);

        // TODO: create class MatchingEngine with only one method: (uses DEPENDENCY and FACADE patterns).
        //  Arguments will be InputReader InputParser, OrderProcessor... and the rest of the things needed to simplify MAIN

        for (String line : inputReader.getInput()) {
            if ("FINISH".equals(line)) {
                orderProcessor.getStoringResult().printExecutionMessages();
                break;
            } else {
                inputParser.parseLine(line);
            }
        }
    }


    private static int parseMaxPosition(String[] args) {

        String maxPosStr = Arrays.stream(args)
                .filter(arg -> arg.startsWith("maximum-position="))
                .findFirst()
                .orElse("maximum-position=0");
        return Integer.parseInt(maxPosStr.split("=")[1]);
    }

    //TODO: maven clean should be working.
    // Java program should be able to wrap up the program into jar file (class files and byte code).
}