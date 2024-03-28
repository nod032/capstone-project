package edu.harbourspace.uni;

import edu.harbourspace.uni.orders.*;

import java.util.*;

public class OrderProcessor {
    private final Map<String, Order> mappedOrders = new HashMap<>();
    private int currentPosition = 0;
    List<Trade> trades = new ArrayList<>();

    public void processOrders (List<Order> orders, int maxPosition){
        for(Order order : orders){
            if (order.getOrderType() == OrderType.CANCEL_ORDER){
                mappedOrders.remove(order.getMessageID());
            } else {
                mappedOrders.put(order.getMessageID(), order);
                if (order.getOriginator() == Originator.VE) {
                    executeOrder(order, maxPosition);
                }
            }
        }
    }
    //TODO: 1st example printing 2 orders instead of cumulative one
    //TODO: DF, VE reversed case
    //TODO: if part of DF is executed, keep the other part of Order
    // TODO: DF orders to be accumulated until EXECUTED or CANCELLED.


    public void executeOrder(Order veOrder, int maxPosition){
        List<Order> matchableDFOrders = listMatchableDFOrders(veOrder);
        if(isExecutable(matchableDFOrders, veOrder, maxPosition)){

            int executedSize = veOrder.getSize();
            for(Order matchableDfOrder : matchableDFOrders){
                currentPosition += (veOrder.getSide() == Side.SELL ? executedSize : -executedSize);
                if(matchableDfOrder.getSize() > executedSize) {
                    generateTrade(matchableDfOrder, veOrder.getSize(), veOrder.getPrice());
                    matchableDfOrder.setSize(matchableDfOrder.getSize() - executedSize);
                    mappedOrders.put(matchableDfOrder.getMessageID(), matchableDfOrder);
                    veOrder.setSize(0);
                    veOrder.setOrderStatus(OrderStatus.EXECUTED);
                    break;
                } else {
                    generateTrade(matchableDfOrder, matchableDfOrder.getSize(), veOrder.getPrice());
                    matchableDfOrder.setSize(0);
                    veOrder.setSize(veOrder.getSize() - executedSize);
                    matchableDfOrder.setOrderStatus(OrderStatus.EXECUTED);
                    //matchableDFOrders.removeIf(e -> e.getMessageID().equals(matchableDfOrder.getMessageID()));
                    //TODO: check if works for every case or should be considered specifically
                }
            }
            mappedOrders.put(veOrder.getMessageID(), veOrder);
        }
    }
    public List<Order> listMatchableDFOrders (Order veOrder){
        return new ArrayList<>(mappedOrders.values()
                .stream()
                .filter(order ->
                        order.getProductId() != null &&
                        order.getProductId().equals(veOrder.getProductId()) &&
                                order.getSide() != veOrder.getSide() &&
                                order.getOrderStatus() == OrderStatus.PENDING &&
                                ((order.getSide() == Side.BUY && order.getPrice() >= veOrder.getPrice()) ||
                                        (order.getSide() == Side.SELL && order.getPrice() <= veOrder.getPrice()))
                ).toList());
    }
    public boolean isExecutable(List<Order> matchableDFOrders, Order veOrder, int maxPosition){
        int totalMatchableDFSize = matchableDFOrders.stream().mapToInt(Order::getSize).sum();
        int possiblePosition = currentPosition + (veOrder.getSide() == Side.SELL ? veOrder.getSize() : -veOrder.getSize());
        boolean withinPositionLimit = Math.abs(possiblePosition) <= maxPosition;
        boolean venueSizeLimit = totalMatchableDFSize >= veOrder.getSize();
        return withinPositionLimit && venueSizeLimit;
    }
    public void generateTrade (Order order, int size, double price) {
        Trade trade;
        trade = new Trade(
                order.getSide(),
                size,
                price,
                order.getProductId());
        trades.add(trade);
    }

    public List<Trade> getTrades() {
        return trades;
    }
}




