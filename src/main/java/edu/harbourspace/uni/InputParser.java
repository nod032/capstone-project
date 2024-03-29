package edu.harbourspace.uni;

import edu.harbourspace.uni.orders.*;
import org.apache.log4j.Logger;

import java.util.*;


public class InputParser {
    private static final Logger logger = Logger.getLogger(Main.class);
    public List<Order> parseInput(List<String> input){
        List<Order> orders = new ArrayList<>();
        Order order;
        for(String line : input){
            String[] lineParts = parseLine(line);
            if(!List.of(Originator.VE.toString(), Originator.DF.toString()).contains(lineParts[0])) {
                logger.info("Some wrong input lines were ignored during parsing");
                throw new InputMismatchException("Wrong input for orders");
            }
            if(!validInputString(lineParts)) throw new InputMismatchException();
            if(isCancelOrder(lineParts)){
                order = new Order(Originator.valueOf(lineParts[0]), lineParts[1]);
            } else{
                order = new Order(
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
