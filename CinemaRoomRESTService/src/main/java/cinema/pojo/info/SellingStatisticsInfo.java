package cinema.pojo.info;

import cinema.pojo.Cinema;
import cinema.pojo.Seat;

import java.util.Map;
import java.util.UUID;

public class SellingStatisticsInfo {
    private final int current_income;
    private final int number_of_available_seats;
    private final int number_of_purchased_tickets;

    public SellingStatisticsInfo(Cinema cinema) {
        number_of_available_seats = cinema.getAvailableSeats().size();
        number_of_purchased_tickets = cinema.getPurchasedTickets().size();

        int income = 0;
        for (Map.Entry<UUID, Seat> entry : cinema.getPurchasedTickets().entrySet()) {
            income += entry.getValue().getPrice();
        }
        current_income = income;
    }

    public int getCurrent_income() {
        return current_income;
    }

    public int getNumber_of_available_seats() {
        return number_of_available_seats;
    }

    public int getNumber_of_purchased_tickets() {
        return number_of_purchased_tickets;
    }
}
