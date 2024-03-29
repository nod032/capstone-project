package edu.harbourspace.uni.orders;

public class Order{
    private final Originator originator;
    private final String messageID;
    private Side side;
    private int size;
    private double price;
    private String productId;
    private OrderStatus orderStatus;
    private final OrderType orderType;
    public Order(Originator originator, String messageID, Side side, int size, double price, String productId) {
        this.originator = originator;
        this.messageID = messageID;
        this.side = side;
        this.size = size;
        this.price = price;
        this.productId = productId;
        this.orderStatus = OrderStatus.PENDING;
        this.orderType = OrderType.CREATE_ORDER;
    }
    public Order(Originator originator, String messageID){
        this.originator = originator;
        this.messageID = messageID;
        this.orderType = OrderType.CANCEL_ORDER;
    }

    public String getId() {
        return this.messageID;
    }

    public OrderType getOrderType() {
        return this.orderType;
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
}
