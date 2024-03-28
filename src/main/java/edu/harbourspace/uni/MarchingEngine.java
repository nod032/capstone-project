package edu.harbourspace.uni;

import edu.harbourspace.uni.orders.Order;

import java.util.List;

public class MarchingEngine {
    private final InputReader inputReader;
    private final InputParser inputParser;
    private final OrderProcessor orderProcessor;

    public MarchingEngine(InputReader inputReader,
                          InputParser inputParser,
                          OrderProcessor orderProcessor) {
        this.inputReader = inputReader;
        this.inputParser = inputParser;
        this.orderProcessor = orderProcessor;
    }

    public void executeProcess(int maxPosition){
        List<String> input =  inputReader.readInput();
        List<Order> orders = inputParser.parseInput(input);
        orderProcessor.processOrders(orders, maxPosition);
        List<Trade> trades = orderProcessor.getTrades();
        TradePrinter tradePrinter = new TradePrinter();
        tradePrinter.printTrade(trades);
    }
}
