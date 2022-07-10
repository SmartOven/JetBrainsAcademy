package cinema.util;

public class Cinema {
    private int rows;
    private int columns;
    private boolean[][] seats;

    public Cinema(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.seats = new boolean[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public boolean[][] getSeats() {
        return seats;
    }

    public void setSeats(boolean[][] seats) {
        this.seats = seats;
    }

    public void setPurchased(int row, int column) {
        seats[row][column] = true;
    }

    public boolean isPurchased(int row, int column) {
        return seats[row][column];
    }
}
