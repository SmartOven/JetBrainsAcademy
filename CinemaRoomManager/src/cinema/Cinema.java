package cinema;

import java.util.Scanner;

/**
 * Cinema class
 * @author Yaroslav Panteleev
 */
public class Cinema {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        Cinema cinema = new Cinema(console);

        String userInput;
        while (true) {
            System.out.println();
            cinema.showOptions();
            userInput = console.nextLine();

            // Exit
            if (userInput.equals("0")) {
                break;
            }
            System.out.println();
            switch (userInput) {
                // Print cinema state
                case "1" ->
                        System.out.println(cinema);

                // Book seat in cinema
                case "2" ->
                        cinema.bookSeat();

                // Print cinema statistics
                case "3" ->
                        cinema.printStatistics();
            }
        }
    }

    private final Scanner console;
    private final int rows;
    private final int seats;
    private final char[][] cinema;
    private final int totalSeatsCount;
    private int purchasedTickets;
    private int income;
    private final int totalIncome;

    private Cinema(Scanner console) {
        this.console = console;
        this.rows = inputNumberOfRows();
        this.seats = inputNumberOfSeatsInRow();
        this.cinema = new char[rows][seats];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                cinema[i][j] = 'S';
            }
        }
        this.totalSeatsCount = rows * seats;
        this.purchasedTickets = 0;
        this.income = 0;
        this.totalIncome = countTotalIncome();
    }

    /**
     *
     * @return Valid rows number from user input
     */
    private int inputNumberOfRows() {
        int rows;
        do {
            System.out.println("Enter the number of rows:");
            rows = Integer.parseInt(console.nextLine());

            if (rows < 0) {
                System.out.println("Number of rows can't be less than zero! Try again...");
            }
        } while (rows < 0);
        return rows;
    }

    /**
     * @return Valid seats number from user input
     */
    private int inputNumberOfSeatsInRow() {
        int seats;
        do {
            System.out.println("Enter the number of seats in each row:");
            seats = Integer.parseInt(console.nextLine());

            if (seats < 0) {
                System.out.println("Number of seats can't be less than zero! Try again...");
            }
        } while (seats < 0);
        return seats;
    }

    /**
     * @return String with percentage of purchased tickets rounded to 2 decimal places
     */
    private String countPercentageOfPurchasedTickets() {
        double percentage = (double) (purchasedTickets) / totalSeatsCount * 100;
        percentage = Math.round(percentage * 100) / 100.0;
        StringBuilder stringBuilderPercentage = new StringBuilder();
        stringBuilderPercentage.append(percentage);
        int precision = stringBuilderPercentage.toString().split("\\.")[1].length();
        stringBuilderPercentage.append("0".repeat(Math.max(0, 2 - precision)));
        return stringBuilderPercentage.toString();
    }

    public void printStatistics() {
        System.out.println("Number of purchased tickets: " + purchasedTickets);
        System.out.println("Percentage: " + countPercentageOfPurchasedTickets() + "%");
        System.out.println("Current income: $" + income);
        System.out.println("Total income: $" + totalIncome);
    }

    public void showOptions() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    private int getTicketPrice(int row, int seat) {
        int price;
        if (totalSeatsCount <= 60) {
            price = 10;
        } else {
            if (row < rows / 2) {
                price = 10;
            } else {
                price = 8;
            }
        }
        return price;
    }

    private int countTotalIncome() {
        int total = 0;
        for (int row = 0; row < rows; row++) {
            for (int seat = 0; seat < seats; seat++) {
                total += getTicketPrice(row, seat);
            }
        }
        return total;
    }

    private boolean isNotEmptySeat(int row, int seat) {
        return cinema[row][seat] != 'S';
    }

    private boolean isOutOfBounds(int row, int seat) {
        return !(row >= 0 && row < rows && seat >= 0 && seat < seats);
    }

    /**
     * Booking seat in the cinema
     * 1. Getting valid row and seat from user input from the console
     * 2. Checking if the seat is empty
     * 3. Making seat busy and add info to the statistics
     */
    public void bookSeat() {
        int row, seat;
        while (true) {
            System.out.println("Enter a row number:");
            row = Integer.parseInt(console.nextLine()) - 1;

            System.out.println("Enter a seat number in that row:");
            seat = Integer.parseInt(console.nextLine()) - 1;
            if (isOutOfBounds(row, seat)) {
                System.out.println("\nWrong input!\n");
                continue;
            }
            if (isNotEmptySeat(row, seat)) {
                System.out.println("\nThat ticket has already been purchased!\n");
                continue;
            }
            break;
        }

        int price = getTicketPrice(row, seat);
        System.out.println("\nTicket price: $" + price);
        cinema[row][seat] = 'B';
        purchasedTickets++;
        income += price;
    }

    /**
     * @return Cinema state as string
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cinema:\n ");
        for (int i = 1; i <= seats; i++) {
            stringBuilder.append(" ").append(i);
        }
        stringBuilder.append("\n");
        for (int i = 1; i <= rows; i++) {
            stringBuilder.append(i);
            for (int j = 1; j <= seats; j++) {
                stringBuilder.append(" ").append(cinema[i - 1][j - 1]);
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}