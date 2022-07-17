package numbers.property;

public class Palindromic extends Property {
    @Override
    protected boolean calculateValue() {
        if (value == null) {
            value = isPalindromic();
        }
        return value;
    }

    @Override
    public String getName() {
        return "palindromic";
    }

    private boolean isPalindromic() {
        String n = String.valueOf(num);
        for (int i = 0; i < n.length(); i++) {
            if (n.charAt(i) != n.charAt(n.length() - 1 - i)) {
                return false;
            }
        }
        return true;
    }
}
