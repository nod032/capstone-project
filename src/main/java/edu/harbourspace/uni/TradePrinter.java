package edu.harbourspace.uni;

import org.apache.log4j.Logger;

import java.util.List;

public class TradePrinter {
    private List<Trade> trades;
    private static final Logger logger = Logger.getLogger(Main.class);
    void printTrade (List<Trade> trades){
        for(Trade t : trades){
            System.out.println(STR."\{t.getSide()} \{t.getSize()} \{t.getPrice()} \{t.getProductId()}");
            logger.info(STR."\{t.getSide()} \{t.getSize()} \{t.getPrice()} \{t.getProductId()}");
        }
    }

}
