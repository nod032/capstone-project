package edu.harbourspace.uni;

import edu.harbourspace.uni.orders.Side;

public class Trade {
    private final Side side;
    private final int size;
    private final double price;
    private final String productId;

    public Trade(Side side, int size, double price, String productId) {
        this.side = side;
        this.size = size;
        this.price = price;
        this.productId = productId;
    }

    public Side getSide() {
        return side;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public String getProductId() {
        return productId;
    }
}
