package edu.harbourspace.uni;

import edu.harbourspace.uni.parser.InputParser;
import edu.harbourspace.uni.parser.Order;

import java.util.List;
import java.util.stream.Collectors;

public class MarchingEngine {

    private final OrderProcessor orderProcessor;

    public MarchingEngine(OrderProcessor orderProcessor) {
        this.orderProcessor = orderProcessor;
    }

    public void executeProcess(){
        InputReader reader = new InputReader();
        InputParser parser = new InputParser();
        List<String> input =  reader.getInput();
        List<Order> orders = input.stream()
                .map(s -> parser.parseLine(s.split(" ")))
                .collect(Collectors.toList());
        OrderProcessor orderProcessor = new OrderProcessor();
        orderProcessor.processOrders(orders);
        orderProcessor.getStoringResult().printExecutionMessages();
    }
}
