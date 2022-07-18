package converter;

import java.util.Scanner;

public class Manager {

    public static void work(Scanner scanner) {
        while (true) {
            System.out.println("Enter two numbers in format: {source base} {target base} (To quit type /exit)");
            String userInput = scanner.nextLine();

            if (userInput.equals("/exit")) {
                break;
            }

            String[] info = userInput.split(" ");
            int sourceBase = Integer.parseInt(info[0]);
            int targetBase = Integer.parseInt(info[1]);

            while (true) {
                System.out.println("Enter number in base " + sourceBase + " to convert to base " + targetBase + "  (To go back type /back)");
                userInput = scanner.nextLine();

                if (userInput.equals("/back")) {
                    break;
                }

                convert(userInput, sourceBase, targetBase);
            }
        }
    }

    private static void convert(String number, int sourceBase, int targetBase) {
        ExtendedNumber extendedNumber = new ExtendedNumber(number, sourceBase);
        extendedNumber = extendedNumber.convertToBase(targetBase);
        System.out.println("Conversion result: " + extendedNumber.toString());
    }
}
