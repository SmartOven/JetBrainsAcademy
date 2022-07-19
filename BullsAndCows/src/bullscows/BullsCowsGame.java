package bullscows;

import java.util.HashSet;
import java.util.Set;

public class BullsCowsGame {
    public static String generateSecretCode(int length, int charactersRange) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        String secretCode = new RandomSetBuilder(0, 10).append(length).toString();
        if (length > 10) {
            secretCode += alphabet.substring(0, length - 10);
        }

        String symbolsRange = charactersRange > 10 ? String.format(", a-%c", alphabet.charAt(charactersRange - 11)) : "";
        System.out.printf("The secret is prepared: %s (0-9%s).\n", "*".repeat(length), symbolsRange);
        return secretCode;
    }

    public static boolean isCorrectAnswer(String grade, int length) {
        return String.format("Grade: %d bulls", length).equals(grade);
    }

    public static String getGrade(String answer, String correctAnswer) {
        if (answer.length() != correctAnswer.length()) {
            return "Wrong length of answer!";
        }

        Set<Integer> hasDigits = new HashSet<>();
        for (int i = 0; i < correctAnswer.length(); i++) {
            hasDigits.add(correctAnswer.charAt(i) - '0');
        }

        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < correctAnswer.length(); i++) {
            char digit = answer.charAt(i);
            if (digit == correctAnswer.charAt(i)) {
                bulls++;
            } else if (hasDigits.contains(digit - '0')) {
                cows++;
            }
        }

        if (bulls == 0 && cows == 0) {
            return "None.";
        }

        StringBuilder grade = new StringBuilder("Grade: ");
        if (bulls > 0 && cows > 0) {
            appendBulls(grade, bulls);
            grade.append(" and ");
            appendCows(grade, cows);
        } else {
            if (bulls > 0) {
                appendBulls(grade, bulls);
            } else {
                appendCows(grade, cows);
            }
        }

        return grade.toString();
    }

    private static void appendBulls(StringBuilder grade, int bulls) {
        grade.append(bulls).append(" bull");
        if (bulls > 1) {
            grade.append("s");
        }
    }

    private static void appendCows(StringBuilder grade, int cows) {
        grade.append(cows).append(" cow");
        if (cows > 1) {
            grade.append("s");
        }
    }
}
