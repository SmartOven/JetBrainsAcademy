package cinema.controllers;

import cinema.Main;
import cinema.util.Cinema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AvailableSeatsController {
    @GetMapping("/seats")
    public AvailableSeatsInfo getAvailableSeats() {
        Cinema cinema = Main.cinema;

        return new AvailableSeatsInfo(cinema);
    }

    private static class AvailableSeatsInfo {
        public int total_rows;
        public int total_columns;
        public List<Seat> available_seats;

        private static class Seat {
            public int row;
            public int column;
            public int price;
            public Seat(int row, int column) {
                this.row = row;
                this.column = column;
                if (row <= 4) {
                    this.price = 10;
                } else {
                    this.price = 8;
                }
            }
        }

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
}
