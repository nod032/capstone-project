package edu.harbourspace.uni;

import edu.harbourspace.uni.parser.InputParser;

public class MarchingEngine {

    public void executeProcess(){
        OrderProcessor orderProcessor = new OrderProcessor(new InputParser(new InputReader()));
        orderProcessor.processOrders();
        orderProcessor.getStoringResult().printExecutionMessages();

    }
}
