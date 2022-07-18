package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * // Usage: for integer or fractional numbers that need to
 * // be stored or converted in bases different from 10
 * //
 * // Code example:
 *
 * ExtendedNumber base10Number = new ExtendedNumber("123.45678");
 * ExtendedNumber base16Number = base10Number.convertToBase(16);
 * // = 7b.74ef9
 *
 * ExtendedNumber backToBase10 = base16Number.convertToBase(10);
 * // = 123.45678
 * </pre>
 */
public class ExtendedNumber {
    private static final List<Character> possibleDigits;

    static {
        possibleDigits = new ArrayList<>();
        char[] digitsArray = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (char digit : digitsArray) {
            possibleDigits.add(digit);
        }
    }

    private final String value;
    private final int base;

    public ExtendedNumber(String value) {
        this(value, 10);
    }

    public ExtendedNumber(String value, int base) {
        this.value = value;
        this.base = base;
    }

    /**
     * Converts ExtendedNumber from current base to targetBase (trough decimal conversation)
     *
     * @param targetBase base to be converted to
     * @return ExtendedNumber with base = targetBase
     */
    public ExtendedNumber convertToBase(int targetBase) {
        if (targetBase == this.base) {
            return this;
        }

        String[] numberParts = value.split("\\.");
        String convertedIntegerPart = convertIntegerPartFromDecimal(
                convertIntegerPartToDecimal(numberParts[0]), targetBase
        );

        String convertedFractionalPart = null;
        if (numberParts.length > 1) {
            convertedFractionalPart = convertFractionalPartFromDecimal(convertFractionalPartToDecimal(numberParts[1]), targetBase);
        }

        String convertedNumber = convertedIntegerPart;
        if (convertedFractionalPart != null) {
            convertedNumber += "." + convertedFractionalPart;
        }

        return new ExtendedNumber(convertedNumber, targetBase);
    }

    /**
     * Converts integerPart from its base to base 10
     *
     * @param integerPart integerPart of the number
     * @return converted integerPart
     */
    private String convertIntegerPartToDecimal(String integerPart) {
        if (base == 10) {
            return integerPart;
        }
        BigInteger baseMultiplier = new BigInteger("1");
        final BigInteger baseToBigInteger = new BigInteger(String.valueOf(base));
        BigInteger numberValue = new BigInteger("0");

        // Converts digit into decimal digit and multiplies it by the powered base, then adds result to the numberValue
        for (int power = 0; power < integerPart.length(); power++) {
            numberValue = numberValue.add(new BigInteger(charToInt(integerPart.charAt(integerPart.length() - 1 - power))).multiply(baseMultiplier));
            baseMultiplier = baseMultiplier.multiply(baseToBigInteger);
        }

        return numberValue.toString();
    }

    /**
     * Converts integerPart from base 10 to targetBase
     *
     * @param decimalIntegerPart integerPart of the number in base 10
     * @param targetBase         base to be converted to
     * @return converted integerPart
     */
    private String convertIntegerPartFromDecimal(String decimalIntegerPart, int targetBase) {
        return new BigInteger(decimalIntegerPart).toString(targetBase);
    }

    /**
     * Converts fractionalPart from its base to base 10
     *
     * @param fractionalPart fractionalPart of the number
     * @return converted fractionalPart
     */
    private String convertFractionalPartToDecimal(String fractionalPart) {
        if (base == 10) {
            return fractionalPart;
        }

        BigDecimal result = BigDecimal.ZERO;
        BigDecimal bigDecimalBase = new BigDecimal(String.valueOf(base));
        BigDecimal divisorBase = new BigDecimal("1");
        for (char digit : fractionalPart.toCharArray()) {
            divisorBase = divisorBase.divide(bigDecimalBase, 15, RoundingMode.HALF_UP);
            result = result.add(new BigDecimal(charToInt(digit)).multiply(divisorBase));
        }

        String[] resultSplit = result.toString().split("\\.");
        return resultSplit[resultSplit.length - 1];
    }

    /**
     * Converts fractionalPart from base 10 to targetBase
     *
     * @param decimalFractionalPart fractionalPart of the number in base 10
     * @param targetBase            base to be converted to
     * @return converted fractionalPart
     */
    private String convertFractionalPartFromDecimal(String decimalFractionalPart, int targetBase) {
        BigDecimal fractionalNumber = new BigDecimal("0." + decimalFractionalPart);
        BigDecimal base = new BigDecimal(String.valueOf(targetBase));

        BigDecimal integerPart, fractionalPart;
        StringBuilder convertedFractionalPart = new StringBuilder();

        // Count 15 digits after dot (for the higher accuracy) and then rounds to 5 by the 6th digit
        for (int afterDotDigit = 0; afterDotDigit < 15; afterDotDigit++) {
            fractionalNumber = fractionalNumber.multiply(base);

            // Splitting result to integer and fractional parts
            integerPart = fractionalNumber.setScale(0, RoundingMode.DOWN);
            fractionalPart = fractionalNumber.subtract(integerPart);

            // Adding integerPart to the result and assign fractionalNumber to it's fractionalPart
            convertedFractionalPart.append(intToChar(Integer.parseInt(integerPart.toString())));
            fractionalNumber = fractionalPart;
        }

        return halfUpRound(convertedFractionalPart.toString(), targetBase);
    }

    private String halfUpRound(String convertedFractionalPartString, int convertableBase) {
        char[] chars = convertedFractionalPartString.toCharArray();

        // Getting int value of 6th digit (e.g. a = 10, z = 35)
        // If it is half or more than base -> round up 5th digit
        int sixthDigitIntValue = Integer.parseInt(charToInt(chars[5]));
        if (sixthDigitIntValue >= convertableBase / 2) {
            int roundedUpFifthDigitValue = Integer.parseInt(charToInt(chars[4])) + 1;
            if (roundedUpFifthDigitValue == convertableBase) {
                roundedUpFifthDigitValue--;
            }
            chars[4] = intToChar(roundedUpFifthDigitValue).charAt(0);
        }

        return String.valueOf(chars, 0, 5);
    }

    /**
     * Converts number digit to int (used for bases 11-36)
     *
     * @param c Character in from '0' to 'z'
     * @return Integer from 0 to 35
     */
    private String charToInt(char c) {
        return String.valueOf(possibleDigits.indexOf(c));
    }

    /**
     * Converts int to number digit (used for bases 11-36)
     *
     * @param i Integer from 0 to 35
     * @return Character in from '0' to 'z'
     */
    private String intToChar(int i) {
        return String.valueOf(possibleDigits.get(i));
    }

    @Override
    public String toString() {
        return value;
    }

    public String toString(int radix) {
        return convertToBase(radix).toString();
    }
}
