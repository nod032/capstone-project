package edu.harbourspace.uni;

import edu.harbourspace.uni.orders.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class TestCases {
    private final Scanner scanner = mock(Scanner.class);
    private final InputReader inputReader = mock(InputReader.class);
    private final InputParser inputParser = mock(InputParser.class);
    private final OrderProcessor orderProcessor = new OrderProcessor();
    private final TradePrinter tradePrinter = new TradePrinter();
    private final MarchingEngine marchingEngine =
            new MarchingEngine(inputReader, inputParser, orderProcessor, tradePrinter);
    @Test
    public void testExample1() {
        int maxPosition = 100000;
        given(scanner.nextLine()).willReturn(String.valueOf(maxPosition));
        List<String> inputLines = List.of(
                "DF ID1 BUY 1000 98.0 VEX123",
                "DF ID2 BUY 75 98.0 VEX123",
                "VE ID01 SELL 1075 98.0 VEX123");
        List<Order> mockedOrders = List.of(
                new Order(Originator.DF, "ID1", Side.BUY, 1000, 98.0, "VEX123"),
                new Order(Originator.DF, "ID2", Side.BUY, 75, 98.0, "VEX123"),
                new Order(Originator.VE, "ID01", Side.SELL, 1075, 98.0, "VEX123"));
        given(inputReader.readInput()).willReturn(inputLines);
        given(inputParser.parseInput(inputLines)).willReturn(mockedOrders);
        marchingEngine.executeProcess(maxPosition);
        List<String> executedTrades = tradePrinter.getExecutedTrades();
        List<String> expectedTrades = List.of(
                "BUY 1000 98.0 VEX123",
                "BUY 75 98.0 VEX123"
        );
        assertEquals(expectedTrades, executedTrades);
    }
    @Test
    public void testExample2() {
        int maxPosition = 100000;
        given(scanner.nextLine()).willReturn(String.valueOf(maxPosition));
        List<String> inputLines = List.of(
                "DF ID1 BUY 1000 99.6 VEX123",
                "DF ID2 BUY 1000 95.6 VEX123",
                "VE ID01 SELL 1000 98.0 VEX123");
        List<Order> mockedOrders = List.of(
                new Order(Originator.DF, "ID1", Side.BUY, 1000, 99.6, "VEX123"),
                new Order(Originator.DF, "ID2", Side.BUY, 1000, 95.6, "VEX123"),
                new Order(Originator.VE, "ID01", Side.SELL, 1000, 98.0, "VEX123"));
        given(inputReader.readInput()).willReturn(inputLines);
        given(inputParser.parseInput(inputLines)).willReturn(mockedOrders);
        marchingEngine.executeProcess(maxPosition);
        List<String> executedTrades = tradePrinter.getExecutedTrades();
        List<String> expectedTrades = List.of(
                "BUY 1000 98.0 VEX123"
        );
        assertEquals(expectedTrades, executedTrades);
    }
    @Test
    public void testExample3() {
        int maxPosition = 100000;
        given(scanner.nextLine()).willReturn(String.valueOf(maxPosition));
        List<String> inputLines = List.of(
                "DF ID1 BUY 1000 99.6 VEX123",
                "VE ID01 SELL 2000 99.6 VEX123");
        List<Order> mockedOrders = List.of(
                new Order(Originator.DF, "ID1", Side.BUY, 1000, 99.6, "VEX123"),
                new Order(Originator.VE, "ID01", Side.SELL, 2000, 99.6, "VEX123"));
        given(inputReader.readInput()).willReturn(inputLines);
        given(inputParser.parseInput(inputLines)).willReturn(mockedOrders);
        marchingEngine.executeProcess(maxPosition);
        List<String> executedTrades = tradePrinter.getExecutedTrades();
        List<String> expectedTrades = List.of();
        assertEquals(expectedTrades, executedTrades);
    }
    @Test
    public void testExample4() {
        int maxPosition = 5000;
        given(scanner.nextLine()).willReturn(String.valueOf(maxPosition));
        List<String> inputLines = List.of(
                "DF ID1 BUY 5000 110.0 VEX123",
                "VE ID01 SELL 5000 100.0 VEX123",
                "DF ID2 BUY 1000 98.0 VEX123",
                "VE ID02 SELL 700 98.0 VEX123");
        List<Order> mockedOrders = List.of(
                new Order(Originator.DF, "ID1", Side.BUY, 5000, 110.0, "VEX123"),
                new Order(Originator.VE, "ID01", Side.SELL, 5000, 100.0, "VEX123"),
                new Order(Originator.DF, "ID2", Side.BUY, 1000, 98.0, "VEX123"),
                new Order(Originator.VE, "ID02", Side.SELL, 700, 98.0, "VEX123"));
        given(inputReader.readInput()).willReturn(inputLines);
        given(inputParser.parseInput(inputLines)).willReturn(mockedOrders);
        marchingEngine.executeProcess(maxPosition);
        List<String> executedTrades = tradePrinter.getExecutedTrades();
        List<String> expectedTrades = List.of(
                "BUY 5000 100.0 VEX123"
        );
        assertEquals(expectedTrades, executedTrades);
    }
    @Test
    public void testExample5() {
        int maxPosition = 100000;
        given(scanner.nextLine()).willReturn(String.valueOf(maxPosition));
        List<String> inputLines = List.of(
                "DF ID1 BUY 400 100.0 VEX789",
                "VE ID01 SELL 300 100.0 VEX789",
                "DF ID1 cancel",
                "VE ID02 SELL 100 100.0 VEX789");
        List<Order> mockedOrders = List.of(
                new Order(Originator.DF, "ID1", Side.BUY, 400, 100.0, "VEX789"),
                new Order(Originator.VE, "ID01", Side.SELL, 300, 100.0, "VEX789"),
                new Order(Originator.DF, "ID1"),
                new Order(Originator.VE, "ID02", Side.SELL, 100, 100.0, "VEX789"));
        given(inputReader.readInput()).willReturn(inputLines);
        given(inputParser.parseInput(inputLines)).willReturn(mockedOrders);
        marchingEngine.executeProcess(maxPosition);
        List<String> executedTrades = tradePrinter.getExecutedTrades();
        List<String> expectedTrades = List.of(
                "BUY 300 100.0 VEX789"
        );
        assertEquals(expectedTrades, executedTrades);
    }
}
