package edu.harbourspace.uni;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TradePrinter {
    private List<String> executedTrades;
    private static final Logger logger = Logger.getLogger(Main.class);
    public void processTrades(List<Trade> trades){
        executedTrades = new ArrayList<>();
        for(Trade t : trades){
            executedTrades.add(STR."\{t.getSide()} \{t.getSize()} \{t.getPrice()} \{t.getProductId()}");
        }
    }
    public void logTrades (){
        for(String executedTrade : executedTrades){
            logger.info(executedTrade);
        }
    }

    public List<String> getExecutedTrades() {
        return executedTrades;
    }
}
