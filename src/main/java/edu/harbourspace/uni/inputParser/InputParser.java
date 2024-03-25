package edu.harbourspace.uni.inputParser;

import edu.harbourspace.uni.OrderProcessor;

public class InputParser {
    private final OrderProcessor orderProcessor;

    public InputParser(OrderProcessor orderProcessor) {
        this.orderProcessor = orderProcessor;
    }

    public void parseLine(String line) {
        // TODO: return ORDER instead of VOID.
        orderProcessor.processLine(line);
    }
}
