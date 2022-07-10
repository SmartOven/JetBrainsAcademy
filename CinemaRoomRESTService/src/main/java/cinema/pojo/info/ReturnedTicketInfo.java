package cinema.pojo.info;

public class ReturnedTicketInfo {
    private final SeatInfo returned_ticket;

    public ReturnedTicketInfo(SeatInfo returned_ticket) {
        this.returned_ticket = returned_ticket;
    }

    public SeatInfo getReturned_ticket() {
        return returned_ticket;
    }
}
