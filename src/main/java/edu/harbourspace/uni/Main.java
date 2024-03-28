package edu.harbourspace.uni;

public class Main {
    public static void main(String[] args) {

        InputReader inputReader = new InputReader();
        InputParser inputParser = new InputParser();
        OrderProcessor orderProcessor = new OrderProcessor();
        MarchingEngine marchingEngine = new MarchingEngine(
                inputReader, inputParser, orderProcessor);
        marchingEngine.executeProcess(inputReader.getMaxPosition());

        /*
//TODO:
        Output to be logged into file instead of printing
        log4j... dont forget!

    case: DF SELL ... 25.. 627
          VE BUY .... 10.. 630
 result:  DF SELL ... 10.. 630

     case: DF Buy ... 25.. 627
          VE Sell .... 10.. 621
 result:  DF Buy ... 10.. 621

 case:    DF Buy ... 10.. 627
          VE Sell .. 25.. 620
 result:  DF Buy ... 10.. 620

         */

    }
}