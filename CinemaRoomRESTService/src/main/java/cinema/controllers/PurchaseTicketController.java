package cinema.controllers;

import cinema.Main;
import cinema.exceptions.CustomRequestException;
import cinema.pojo.Cinema;
import cinema.pojo.Seat;
import cinema.pojo.info.ReturnedTicketInfo;
import cinema.pojo.info.SeatInfo;
import cinema.pojo.info.PurchasedTicketInfo;
import cinema.pojo.info.TicketTokenInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PurchaseTicketController {

    @PostMapping("/purchase")
    public PurchasedTicketInfo purchaseTicketByRequestBody(@RequestBody SeatInfo seatInfo) {
        return purchaseTicketIfAvailable(seatInfo.getRow(), seatInfo.getColumn());
    }

    private PurchasedTicketInfo purchaseTicketIfAvailable(int row, int column) {
        Cinema cinema = Main.cinema;
        row--;
        column--;

        // Checking if row or column are out of bounds
        if (row < 0 || row >= cinema.getRows() || column < 0 || column >= cinema.getRows()) {
            throw new CustomRequestException("The number of a row or a column is out of bounds!");
        }

        Seat purchasedSeat = cinema.purchaseSeat(row, column);
        if (purchasedSeat == null) {
            throw new CustomRequestException("The ticket has been already purchased!");
        }

        return new PurchasedTicketInfo(purchasedSeat);
    }

    @PostMapping("/return")
    public ReturnedTicketInfo returnTicketByUUID(@RequestBody TicketTokenInfo ticketToken) {
        Cinema cinema = Main.cinema;
        UUID token = ticketToken.getToken();

        // Getting returned seat by token (if it exists)
        Seat returnedSeat = cinema.returnSeat(token);

        // If it doesn't exist -> throw exception
        if (returnedSeat == null) {
            throw new CustomRequestException("Wrong token!");
        }

        return new ReturnedTicketInfo(new SeatInfo(returnedSeat));
    }
}
