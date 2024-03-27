package edu.harbourspace.uni;

import edu.harbourspace.uni.parser.*;

import java.util.*;

public class OrderProcessor {
    private final Map<String, Order> orders = new HashMap<>();
    private final List<String> executedOrders = new ArrayList<>();
    private int currentPosition = 0;
    private StoringResult storingResult = new StoringResult();
    private InputParser inputParser;
    List<String> inputLines;

    public OrderProcessor(InputParser inputParser) {
        this.inputParser = inputParser;
        inputLines = inputParser.getInputReader().getInput();
    }

    // TODO: Orderprocessor.process(Order)

    public void processOrders() {

        for(String line : inputLines){
            String[] parts = line.split(" ");

            // Throw exception for wrong amount of input data
            if(parts.length != 6 && parts.length != 3) {
                throw new InputMismatchException();
            }

            //TODO: if only part of the order is executed, keep the other part in PENDING
            if (parts.length == 3 && "cancel".equalsIgnoreCase(parts[2])) {
                for(Order o : orders.values()){
                    if(Objects.equals(o.getMessageID(), parts[1])) {
                        o.setOrderStatus(OrderStatus.CANCELLED);
                        break;
                    }
                }
                continue;
            }

            Order order = inputParser.parseLine(parts);
            orders.put(order.getMessageID(), order);

            if (order.getOriginator() == Originator.VE) {
                executeOrderImmediately(order);
            }
        }
    }

    private void executeOrderImmediately(Order veOrder) {
//TODO: separate the body into several methods, 1st example printing 2 orders instead of cumulative one
        List<Order> matchableDFOrders = new ArrayList<>(orders.values()
                .stream()
                .filter(order -> order.getProductId()
                        .equals(veOrder.getProductId()) &&
                        order.getSide() != veOrder.getSide() &&
                        order.getOrderStatus() == OrderStatus.PENDING)
                .toList());

        int totalMatchableDFSize = matchableDFOrders.stream().mapToInt(Order::getSize).sum();
        //System.out.println("totalMatchableDFSize = " + totalMatchableDFSize);
        //System.out.println("matchableDFOrders = " + matchableDFOrders);

        //TODO: if part of DF is executed, keep the other part of Order
        for (Order dfOrder : matchableDFOrders) {
            // Here we check if execution is possible based on price and position limits
            if (canExecuteOrder(dfOrder, veOrder, totalMatchableDFSize)
                    && veOrder.getSize() <= totalMatchableDFSize) {
                //int executedSize = Math.min(dfOrder.getSize(), veOrder.getSize());
                int executedSize = veOrder.getSize();

                // TODO: DF orders to be accumulated until EXECUTED or CANCELLED.
                if (dfOrder.getSide() == Side.BUY) {
                    currentPosition += executedSize;
                } else {
                    currentPosition -= executedSize;
                }

                dfOrder.setSize(dfOrder.getSize() - executedSize);
                veOrder.setSize(veOrder.getSize() - executedSize);
                orders.put(veOrder.getMessageID(),
                        new Order(veOrder.getOriginator(),
                                veOrder.getMessageID(),
                                veOrder.getSide(),
                                veOrder.getSize(),
                                veOrder.getPrice(),
                                veOrder.getProductId()
                        ));

                // Mark orders as executed if their size is zero
                if (dfOrder.getSize() == 0){
                    //System.out.println("DF=0:  For order: " + dfOrder +  ", size = " + dfOrder.getSize());
                    dfOrder.setOrderStatus(OrderStatus.EXECUTED);
                    matchableDFOrders.removeIf(e -> e.getMessageID().equals(dfOrder.getMessageID()));
//                } else {
//                    dfOrder.setOrderStatus(OrderStatus.PENDING);
                }
                if (veOrder.getSize() == 0) {
                    //System.out.println("VE = 0:  For order: " + veOrder +  ", size = " + veOrder.getSize());
                    //System.out.println("VE = 0:  For order: " + dfOrder +  ", size = " + dfOrder.getSize());
                    veOrder.setOrderStatus(OrderStatus.EXECUTED);
                    //matchableDFOrders.removeIf(e -> e.getMessageID().equals(veOrder.getMessageID()));
                }

                String executionMessage = String.format("%s\t%d\t%.1f\t%s",
                        dfOrder.getSide(), executedSize, veOrder.getPrice(), dfOrder.getProductId());
                storingResult.addExecutionMessage(executionMessage);

                if (veOrder.getSize() == 0) break; // Exit if the VE order is fully matched
            }
        }
    }

    private boolean canExecuteOrder(Order dfOrder, Order veOrder, int totalMatchableDFSize) {

        boolean priceCompatibilityMatches =
                (dfOrder.getSide() == Side.BUY && dfOrder.getPrice() >= veOrder.getPrice()) ||
                (dfOrder.getSide() == Side.SELL && dfOrder.getPrice() <= veOrder.getPrice()) ;

        int possiblePosition = currentPosition + (dfOrder.getSide() == Side.BUY ? veOrder.getSize() : -veOrder.getSize());
        boolean withinPositionLimit = Math.abs(possiblePosition) <= inputParser.getInputReader().getMaxPosition();
        boolean venueSizeLimit = totalMatchableDFSize >= veOrder.getSize();
        return priceCompatibilityMatches && withinPositionLimit && venueSizeLimit;
    }

    public StoringResult getStoringResult() {
        return storingResult;
    }
}




