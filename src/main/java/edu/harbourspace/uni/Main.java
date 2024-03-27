package edu.harbourspace.uni;

import edu.harbourspace.uni.parser.InputParser;

public class Main {
    public static void main(String[] args) {
        InputParser inputParser = new InputParser();
        OrderProcessor orderProcessor = new OrderProcessor();
        MarchingEngine marchingEngine = new MarchingEngine(inputParser, orderProcessor...);
        marchingEngine.executeProcess();
    }
}