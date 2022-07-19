package cinema.controllers;

import cinema.Main;
import cinema.exceptions.CustomRequestException;
import cinema.pojo.Cinema;
import cinema.pojo.info.CustomRequestExceptionInfo;
import cinema.pojo.info.SellingStatisticsInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SellingStatisticsController {

    @PostMapping("/stats")
    public SellingStatisticsInfo getSellingStatistics(@RequestParam String password) {
        Cinema cinema = Main.cinema;

        if (!cinema.isCorrectPassword(password)) {
            throw new CustomRequestException("The password is wrong!");
        }

        return new SellingStatisticsInfo(cinema);
    }
}
