package edu.harbourspace.uni.parser;

public class Order {
    private final Originator originator;
    private final String messageID;
    private final Side side;
    private int size; // Non-final to allow modification when partially executed
    private final double price;
    private final String productId;
    private OrderStatus orderStatus;

    public Order(Originator originator, String messageID, Side side, int size, double price, String productId) {
        this.originator = originator;
        this.messageID = messageID;
        this.side = side;
        this.size = size;
        this.price = price;
        this.productId = productId; // TODO: make them as separate class with only one string field.
        this.orderStatus = OrderStatus.PENDING;
    }

    public Originator getOriginator() {
        return originator;
    }

    public String getMessageID() {
        return messageID;
    }

    public Side getSide() {
        return side;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public String getProductId() {
        return productId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    // Mutators
    public void setSize(int size) {
        this.size = size;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%s\t%d\t%.3f\t%s",
                originator, messageID, side, size, price, productId);
    }
}
