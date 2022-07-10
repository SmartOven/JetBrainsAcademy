package cinema.pojo;

import java.util.*;

public class Cinema {
    private int rows;
    private int columns;
    private final List<Seat> seats;
    private final TreeSet<Seat> availableSeats;
    private final Map<UUID, Seat> purchasedTickets;

    public Cinema(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        seats = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                seats.add(new Seat(i + 1, j + 1));
            }
        }

        // Setting up available seats set
        availableSeats = new TreeSet<>((o1, o2) -> {
            int o1Row = o1.getRow();
            int o2Row = o2.getRow();
            if (o1Row != o2Row) {
                return o1Row - o2Row;
            } else {
                return o1.getColumn() - o2.getColumn();
            }
        });

        // Initially every seat is available and purchased tickets doesn't exist
        availableSeats.addAll(seats);
        purchasedTickets = new HashMap<>();
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

    public TreeSet<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public Map<UUID, Seat> getPurchasedTickets() {
        return purchasedTickets;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    /**
     * Purchasing seat in cinema
     * @param row seat's row
     * @param column seat's column
     * @return purchased seat or null if it is already busy
     */
    public Seat purchaseSeat(int row, int column) {
        // Getting seat and checking if it is already busy
        Seat seat = seats.get(row * column + column);
        if (seat.isBusy()) {
            return null;
        }

        UUID token = UUID.randomUUID();

        // Making it busy (setting up purchase UUID for it)
        seat.setPurchaseToken(token);

        // Remove it from available seats and add ticket to the map
        availableSeats.remove(seat);
        purchasedTickets.put(token, seat);

        return seat;
    }

    /**
     * Returning ticket by it's token
     * @param token ticket's token
     * @return returned seat or null if the token doesn't exist
     */
    public Seat returnSeat(UUID token) {
        Seat seat = purchasedTickets.getOrDefault(token, null);
        if (seat == null) {
            return null;
        }

        // Make it free again
        seat.setPurchaseToken(null);

        // Add it to available seats and remove ticket from the map
        availableSeats.add(seat);
        purchasedTickets.remove(token);

        return seat;
    }
}
