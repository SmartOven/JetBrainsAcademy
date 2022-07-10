package cinema;

import cinema.pojo.Cinema;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static Cinema cinema;

    public static void main(String[] args) {
        cinema = new Cinema(9, 9);

        SpringApplication.run(Main.class, args);
    }

}
