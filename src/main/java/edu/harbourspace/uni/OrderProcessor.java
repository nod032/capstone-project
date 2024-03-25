package edu.harbourspace.uni;

import edu.harbourspace.uni.inputParser.*;

import java.util.*;
import java.util.stream.Collectors;

public class OrderProcessor {
    private final int maxPosition;
    private final Map<String, Order> orders = new HashMap<>();
    private final List<String> executedOrders = new ArrayList<>();
    private int currentPosition = 0;
    private StoringResult storingResult = new StoringResult();

    public OrderProcessor(int maxPosition) {
        this.maxPosition = maxPosition;
    }

    // TODO: Orderprocessor.process(Order)

    public void processLine(String line) {

        String[] parts = line.split("\t");
        String originatorStr = parts[0];
        Originator originator = Originator.valueOf(originatorStr);
        String messageId = parts[1];

        if(parts.length != 6 && parts.length != 3) {
            return;  // This implies the input line is not valid.
            //throw some exception
        }

        if (parts.length == 3 && "CANCEL".equals(parts[2])) {
            Order orderToCancel = orders.get(messageId);
            if (orderToCancel != null) {
                orderToCancel.setOrderStatus(OrderStatus.CANCELLED);
            }
            return;
        }

        Side side = Side.valueOf(parts[2]);
        int size = Integer.parseInt(parts[3]);
        double price = Double.parseDouble(parts[4]);
        String productId = parts[5];

        Order order = new Order(originator, messageId, side, size, price, productId);
        orders.put(messageId, order);

        if (originator == Originator.VE) {
            executeOrderImmediately(order);
        }
    }

    private void executeOrderImmediately(Order veOrder) {

        List<Order> matchableDFOrders = orders.values()
                .stream()
                .filter(order -> order.getProductId()
                        .equals(veOrder.getProductId()) &&
                            order.getSide() != veOrder.getSide() &&
                            order.getOrderStatus() == OrderStatus.PENDING)
                .toList();

        for (Order dfOrder : matchableDFOrders) {
            // Here we check if execution is possible based on price and position limits
            if (canExecuteOrder(dfOrder, veOrder)) {
                int executedSize = Math.min(dfOrder.getSize(), veOrder.getSize());

                // TODO: DF orders to be accumulated until EXECUTED or CANCELLED.
                if (dfOrder.getSide() == Side.BUY) {
                    currentPosition += executedSize;
                } else {
                    currentPosition -= executedSize;
                }

                // TODO: Have to change - VE can't be partially executed
                dfOrder.setSize(dfOrder.getSize() - executedSize);
                veOrder.setSize(veOrder.getSize() - executedSize);

                // Mark orders as executed if their size is zero
                if (dfOrder.getSize() == 0) dfOrder.setOrderStatus(OrderStatus.EXECUTED);
                if (veOrder.getSize() == 0) veOrder.setOrderStatus(OrderStatus.EXECUTED);

                String executionMessage = String.format("%s\t%d\t%.3f\t%s",
                        dfOrder.getSide(), executedSize, dfOrder.getPrice(), dfOrder.getProductId());
                storingResult.addExecutionMessage(executionMessage);

                if (veOrder.getSize() == 0) break; // Exit if the VE order is fully matched
            }
        }
    }

    private boolean canExecuteOrder(Order dfOrder, Order veOrder) {

        boolean priceCompatibilityMatches = (dfOrder.getSide() == Side.BUY && dfOrder.getPrice() >= veOrder.getPrice()) ||
                (dfOrder.getSide() == Side.SELL && dfOrder.getPrice() <= veOrder.getPrice());

        int possiblePosition = currentPosition + (dfOrder.getSide() == Side.BUY ? veOrder.getSize() : -veOrder.getSize());
        boolean withinPositionLimit = Math.abs(possiblePosition) <= maxPosition;
        return priceCompatibilityMatches && withinPositionLimit;
    }

    public StoringResult getStoringResult() {
        return storingResult;
    }
}
