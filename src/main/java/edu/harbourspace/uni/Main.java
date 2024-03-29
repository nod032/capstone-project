package edu.harbourspace.uni;

public class Main {
    public static void main(String[] args) {
        InputReader inputReader = new InputReader();
        InputParser inputParser = new InputParser();
        OrderProcessor orderProcessor = new OrderProcessor();
        TradePrinter tradePrinter = new TradePrinter();
        MarchingEngine marchingEngine = new MarchingEngine(
                inputReader, inputParser, orderProcessor, tradePrinter);
        marchingEngine.executeProcess(inputReader.getMaxPosition());
    }
}