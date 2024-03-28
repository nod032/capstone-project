package edu.harbourspace.uni.orders;

public abstract class Order {
    public abstract String getId();
    public abstract OrderType getOrderType();
    public abstract Originator getOriginator();

    public abstract String getMessageID();

    public abstract Side getSide();

    public abstract int getSize();

    public abstract double getPrice();

    public abstract String getProductId();

    public abstract OrderStatus getOrderStatus();
    public abstract void setSize(int size);

    public abstract void setOrderStatus(OrderStatus orderStatus);


}
