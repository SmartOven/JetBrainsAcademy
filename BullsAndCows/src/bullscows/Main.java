package bullscows;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        String input;

        System.out.println("Input the length of the secret code:");
        int length;
        input = console.nextLine();
        try {
            length = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", input);
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        int uniqueCharactersRange;
        input = console.nextLine();
        try {
            uniqueCharactersRange = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", input);
            return;
        }

        if (length > uniqueCharactersRange || length <= 0) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", length, uniqueCharactersRange);
            return;
        }

        if (uniqueCharactersRange > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }

        String secretCode = BullsCowsGame.generateSecretCode(length, uniqueCharactersRange);
        System.out.println("Okay, let's start a game!");

        int turnCounter = 1;
        while (true) {
            System.out.printf("Turn %d:\n", turnCounter);
            String userAnswer = console.nextLine();

            String grade = BullsCowsGame.getGrade(userAnswer, secretCode);
            System.out.println(grade);

            if (BullsCowsGame.isCorrectAnswer(grade, length)) {
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }
            turnCounter++;
        }
    }
}
