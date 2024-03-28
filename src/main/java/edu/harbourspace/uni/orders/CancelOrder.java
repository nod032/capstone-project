package edu.harbourspace.uni.orders;

public class CancelOrder extends Order{
    private final Originator originator;
    private final String messageID;
    private final OrderType orderType;


    public CancelOrder(Originator originator, String messageID) {
        this.originator = originator;
        this.messageID = messageID;
        this.orderType = OrderType.CANCEL_ORDER;
    }

    @Override
    public String getId() {
        return this.messageID;
    }

    @Override
    public OrderType getOrderType() {
        return this.orderType;
    }

    public Originator getOriginator(){
        return this.originator;
    }

    @Override
    public String getMessageID() {
        return this.messageID;
    }

    @Override
    public Side getSide() {
        return null;
    }

    @Override
    public int getSize() {
        return -1;
    }

    @Override
    public double getPrice() {
        return -1;
    }

    @Override
    public String getProductId() {
        return null;
    }

    @Override
    public OrderStatus getOrderStatus() {
        return null;
    }

    @Override
    public void setSize(int size) {

    }

    @Override
    public void setOrderStatus(OrderStatus orderStatus) {

    }
}
