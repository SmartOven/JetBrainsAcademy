package banking.util;

public class LuhnAlgorithm {
    /**
     *
     * @param s CardNumber with no checkSum digit
     * @return checkSum digit
     */
    public static int getCheckSum(String s) {
        int[] digits = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            digits[i] = s.charAt(i) - '0';
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
