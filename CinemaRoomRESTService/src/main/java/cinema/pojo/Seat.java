package cinema.pojo;

import java.util.UUID;

public class Seat {
    private final int row;
    private final int column;
    private final int price;
    private UUID purchaseToken;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.purchaseToken = null;
        if (row <= 4) {
            this.price = 10;
        } else {
            this.price = 8;
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public UUID getPurchaseToken() {
        return purchaseToken;
    }

    public void setPurchaseToken(UUID purchaseToken) {
        this.purchaseToken = purchaseToken;
    }

    public int getPrice() {
        return price;
    }

    public boolean isBusy() {
        return purchaseToken != null;
    }
}
