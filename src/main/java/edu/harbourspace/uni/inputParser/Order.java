package edu.harbourspace.uni.inputParser;

enum Originator{
    DF, VE;
}

enum Side {
    BUY, SELL
}

public class Order{
    private final Originator originator;
    private final String messageID;
    private final Side side;
    private final int size;
    private final double price;
    private final String productId;
    private static final String terminatorMessage = "FINISH";
    private static final String cancelMessage = "cancel";

    public Order(Originator originator, String messageID) {
        this.originator = originator;
        this.messageID = messageID;
    }

    public Order(Originator originator, String messageID, Side side, int size, double price, String productId) {
        this.originator = originator;
        this.messageID = messageID;
        this.side = side;
        this.size = size;
        this.price = price;
        this.productId = productId;
    }
}