package edu.harbourspace.uni;

import edu.harbourspace.uni.InputReader;
import edu.harbourspace.uni.orders.*;

import java.util.*;

public class InputParser {
    public List<Order> parseInput(List<String> input){
        List<Order> orders = new ArrayList<>();
        Order order;
        for(String line : input){
            String[] lineParts = parseLine(line);
            if(!validInputString(lineParts)) throw new InputMismatchException();
            if(isCancelOrder(lineParts)){
                order = new CancelOrder(Originator.valueOf(lineParts[0]), lineParts[1]);
            } else{
                order = new CreateOrder(
                        Originator.valueOf(lineParts[0]),
                        lineParts[1],
                        Side.valueOf(lineParts[2]),
                        Integer.parseInt(lineParts[3]),
                        Double.parseDouble(lineParts[4]),
                        lineParts[5]);
            }
            orders.add(order);
        }
        return orders;
    }

    public String[] parseLine(String line){
        return line.split(" ");
    }

    public boolean validInputString(String[] lineParts){
        if(lineParts.length != 6 && lineParts.length != 3) {
            throw new InputMismatchException("Number of input elements don't match");
        }
        return true;
    }

    public boolean isCancelOrder(String[] lineParts){
        return Arrays.asList(lineParts).contains("cancel");
    }
}
