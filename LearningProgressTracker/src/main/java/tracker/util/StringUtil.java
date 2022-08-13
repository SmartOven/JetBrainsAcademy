package tracker.util;

public class StringUtil {
    public static Integer[] findLastWhitespacesSequence(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }

        Integer start = null;
        Integer end = null;
        char[] stringChars = s.toCharArray();
        int i = stringChars.length - 1;
        while (i >= 0) {
            if (Character.isWhitespace(stringChars[i])) {
                end = i;
                break;
            }
            i--;
        }
        while (i >= 0) {
            if (!Character.isWhitespace(stringChars[i])) {
                start = i + 1;
                break;
            }
            i--;
        }

        if (start == null) {
            return null;
        }

        return new Integer[]{start, end};
    }
}
