package tictactoe;

import java.util.Scanner;

/**
 * Solution for Tic-Tac-Toe project from hyperskill
 * @author Yaroslav Panteleev (https://github.com/SmartOven)
 */
public class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        Game game = new Game();
        System.out.println(game);

        boolean successfulMove;
        do {
            do {
                System.out.print("Enter the coordinates: ");
                successfulMove = game.makeMove(console.nextLine());
            } while (!successfulMove);
            System.out.println(game);
        } while (!game.isFinished());

        System.out.println(game.getGameStateInfo());
    }
}
