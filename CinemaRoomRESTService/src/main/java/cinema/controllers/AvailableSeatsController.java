package cinema.controllers;

import cinema.Main;
import cinema.pojo.info.AvailableSeatsInfo;
import cinema.pojo.Cinema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AvailableSeatsController {
    @GetMapping("/seats")
    public AvailableSeatsInfo getAvailableSeats() {
        Cinema cinema = Main.cinema;

        return new AvailableSeatsInfo(cinema);
    }
}
