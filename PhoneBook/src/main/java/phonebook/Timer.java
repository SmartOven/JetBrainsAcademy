package phonebook;

/**
 * Used for controlling calculation time and for better view of time difference
 */
public class Timer {
    private static long timeBegin;
    private static long timeEnd;

    public static void begin() {
        timeBegin = System.currentTimeMillis();
    }

    public static void end() {
        timeEnd = System.currentTimeMillis();
    }

    public static long getTimeDifferenceMillis() {
        return timeEnd - timeBegin;
    }

    public static String convertMillisToTime(long diff) {
        StringBuilder result = new StringBuilder();
        long second = 1000;        // 1000 ms.
        long minute = 60 * second; // 60 sec.

        result.append(diff / minute).append(" min.");
        diff %= minute;
        result.append(" ").append(diff / second).append(" sec.");
        diff %= second;
        result.append(" ").append(diff).append(" ms.");

        return result.toString();
    }
}
