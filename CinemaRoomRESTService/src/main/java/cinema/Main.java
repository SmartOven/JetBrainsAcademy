package cinema;

import cinema.util.Cinema;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static Cinema cinema;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        cinema = new Cinema(9, 9);
    }

}
