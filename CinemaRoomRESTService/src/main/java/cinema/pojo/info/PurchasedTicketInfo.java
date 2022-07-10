package cinema.pojo.info;

import cinema.pojo.Seat;

import java.util.UUID;

public class PurchasedTicketInfo {
    private final UUID token;
    private final SeatInfo ticket;

    public PurchasedTicketInfo(Seat seat) {
        token = seat.getPurchaseToken();
        ticket = new SeatInfo(seat);
    }

    public PurchasedTicketInfo(UUID token, SeatInfo ticket) {
        this.token = token;
        this.ticket = ticket;
    }

    public UUID getToken() {
        return token;
    }

    public SeatInfo getTicket() {
        return ticket;
    }
}
