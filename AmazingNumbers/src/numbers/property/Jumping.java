package numbers.property;

import java.util.HashSet;
import java.util.Set;

public class Jumping extends Property {
    private static final Set<Long> allJumpingNumbers;

    static {
        allJumpingNumbers = new HashSet<>();
        calculateJumpingNumbers();
    }

    @Override
    protected boolean calculateValue() {
        if (value == null) {
            value = isJumping();
        }
        return value;
    }

    @Override
    public String getName() {
        return "jumping";
    }

    private boolean isJumping() {
        return allJumpingNumbers.contains(num);
    }

    private static void calculateJumpingNumbers() {
//        int maxLength = String.valueOf(Long.MAX_VALUE).length();
        int maxLength = 12;
        for (int singleDigitJumpingNumber = 1; singleDigitJumpingNumber <= 9; singleDigitJumpingNumber++) {
            addJumpingNumber(singleDigitJumpingNumber, 1, maxLength);
        }
    }

    private static void addJumpingNumber(long jumpingNumber, int length, int maxLength) {
        allJumpingNumbers.add(jumpingNumber);

        if (length == maxLength) {
            return;
        }

        long lastDigit = jumpingNumber % 10;
        jumpingNumber *= 10;
        length++;

        if (lastDigit == 0) {
            addJumpingNumber(jumpingNumber + 1, length, maxLength);
        } else if (lastDigit == 9) {
            addJumpingNumber(jumpingNumber + 8, length, maxLength);
        } else {
            addJumpingNumber(jumpingNumber + (lastDigit - 1), length, maxLength);
            addJumpingNumber(jumpingNumber + (lastDigit + 1), length, maxLength);
        }
    }
}
