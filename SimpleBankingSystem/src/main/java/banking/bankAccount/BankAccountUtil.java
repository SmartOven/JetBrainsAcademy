package banking.bankAccount;

import java.util.Random;

/**
 * Util class for bank accounts
 */
public class BankAccountUtil {
    /**
     * @return generated 4-digit pin code
     */
    public static String generatePinCode() {
        Random randomPinCodeGenerator = new Random();
        String code = String.valueOf(randomPinCodeGenerator.nextInt(10000));
        String leadingZeros = "0".repeat(4 - code.length());
        return leadingZeros + code;
    }

    /**
     *
     * @param cardNumber cardNumber to check
     * @return true if cardNumber is valid
     */
    public static boolean isValidCardNumber(String cardNumber) {
        int cardNumberLength = cardNumber.length();
        if (!(cardNumberLength >= 16 && cardNumberLength <= 19)) {
            return false;
        }

        for (char digit : cardNumber.toCharArray()) {
            if (!Character.isDigit(digit)) {
                return false;
            }
        }

        int checkSumDigit = cardNumber.charAt(cardNumberLength - 1) - '0';
        int calculatedCheckSum = calculateCheckSum(cardNumber.substring(0, cardNumberLength - 1));
        return checkSumDigit == calculatedCheckSum;
    }

    /**
     * Calculates checkSum using Luhn algorithm
     * @param cardNumberWithNoCheckSum cardNumber that has no last (checkSum) digit
     * @return checkSum digit for the given cardNumber
     */
    public static int calculateCheckSum(String cardNumberWithNoCheckSum) {
        int[] digits = new int[cardNumberWithNoCheckSum.length()];
        for (int i = 0; i < cardNumberWithNoCheckSum.length(); i++) {
            digits[i] = cardNumberWithNoCheckSum.charAt(i) - '0';
        }

        // step 1
        for (int i = 0; i < digits.length; i++) {
            if (i % 2 == 0) {
                digits[i] *= 2;
            }
        }

        // step 2
        for (int i = 0; i < digits.length; i++) {
            if (digits[i] > 9) {
                digits[i] -= 9;
            }
        }

        // step 3
        int divisibleBy10 = 0;
        for (int digit : digits) {
            divisibleBy10 += digit;
        }

        if (divisibleBy10 % 10 == 0) {
            return 0;
        }

        // return checkSum
        return 10 - (divisibleBy10 % 10);
    }
}
