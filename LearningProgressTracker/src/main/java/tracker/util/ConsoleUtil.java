package tracker.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ConsoleUtil {

    private static final PrintStream standardOut = System.out;
    private static PrintStream currentOut = System.out;
    private static ByteArrayOutputStream outputStream;

    public static void setCustomSystemOut() {
        outputStream = new ByteArrayOutputStream();
        currentOut = new PrintStream(outputStream);
        System.setOut(currentOut);
    }

    public static void setDefaultSystemOut() {
        System.setOut(standardOut);
        currentOut = standardOut;
    }

    public static PrintStream getCurrentOut() {
        return currentOut;
    }

    public static ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }

    public static String getOutputString() {
        return outputStream.toString();
    }

    public static String[] getOutputLines() {
        return outputStream.toString().split("\r\n");
    }
}
