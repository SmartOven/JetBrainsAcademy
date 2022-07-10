package cinema.pojo.info;

import cinema.pojo.Cinema;
import cinema.pojo.Seat;

import java.util.ArrayList;
import java.util.List;

public class AvailableSeatsInfo {
    public int total_rows;
    public int total_columns;
    public List<SeatInfo> available_seats;

    public AvailableSeatsInfo(Cinema cinema) {
        total_rows = cinema.getRows();
        total_columns = cinema.getColumns();
        available_seats = new ArrayList<>();
        for (Seat seat : cinema.getAvailableSeats()) {
            available_seats.add(new SeatInfo(seat));
        }
    }
}