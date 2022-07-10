package cinema.util;

import java.util.ArrayList;
import java.util.List;

public class AvailableSeatsInfo {
    public int total_rows;
    public int total_columns;
    public List<Seat> available_seats;

    public AvailableSeatsInfo(Cinema cinema) {
        total_rows = cinema.getRows();
        total_columns = cinema.getColumns();
        available_seats = new ArrayList<>();
        boolean[][] seats = cinema.getSeats();
        for (int i = 0; i < total_rows; i++) {
            for (int j = 0; j < total_columns; j++) {
                // If seat is empty
                if (!seats[i][j]) {
                    available_seats.add(new Seat(i + 1, j + 1));
                }
            }
        }
    }
}