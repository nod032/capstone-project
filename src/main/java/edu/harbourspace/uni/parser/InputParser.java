package edu.harbourspace.uni.parser;

import edu.harbourspace.uni.InputReader;

public class InputParser {
    private final InputReader inputReader;

    public InputParser(InputReader inputReader) {
        this.inputReader = inputReader;
    }

    public InputReader getInputReader() { return inputReader; }

    public Order parseLine(String[] lineParts) {

        String originatorStr = lineParts[0];
        Originator originator = Originator.valueOf(originatorStr);
        String messageId = lineParts[1];
        Side side = Side.valueOf(lineParts[2]);
        int size = Integer.parseInt(lineParts[3]);
        double price = Double.parseDouble(lineParts[4]);
        String productId = lineParts[5];
        return new Order(originator, messageId, side, size, price, productId);

    }
}
