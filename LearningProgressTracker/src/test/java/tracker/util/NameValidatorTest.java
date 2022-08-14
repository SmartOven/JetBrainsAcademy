package tracker.util;

import org.junit.jupiter.api.Test;
import tracker.util.validator.NameValidator;

import static org.junit.jupiter.api.Assertions.*;

class NameValidatorTest {

    final static String[] invalidNames = {
            "a b", "aa b", "a bb",                  // too short
            "-a bb", "a- bb", "'a bb", "a' bb",     // wrong placement of ' or -
            "aa -b", "aa b-", "aa 'b", "aa b'",     // wrong placement of ' or -
            "aa bb1", "aa1 bb", "aa* bb", "aa bb*", // invalid characters
            "aa"                                    // second name doesn't exist
    };
    final static String[] validNames = {
            "aa bb", "a-a b-b", "a'a b'b", "a'a-a b'b-b", "aa bb cc"
    };
    final static String[] firstNames = {
            "aa", "a-a", "a'a", "a'a-a", "aa"
    };
    final static String[] secondNames = {
            "bb", "b-b", "b'b", "b'b-b", "bb cc"
    };

    @Test
    void getFirstNameValidInput() {
        int testsCount = validNames.length;
        for (int i = 0; i < testsCount; i++) {
            NameValidator validator = new NameValidator(validNames[i]);
            assertEquals(firstNames[i], validator.getFirstName());
        }
    }

    @Test
    void getSecondNameValidInput() {
        int testsCount = validNames.length;
        for (int i = 0; i < testsCount; i++) {
            NameValidator validator = new NameValidator(validNames[i]);
            assertEquals(secondNames[i], validator.getLastName());
        }
    }

    @Test
    void isValidWithValidInput() {
        for (String validName : validNames) {
            NameValidator validator = new NameValidator(validName);
            assertTrue(validator.isValid());
        }
    }

    @Test
    void getFirstNameInvalidInput() {
        for (String invalidName : invalidNames) {
            NameValidator validator = new NameValidator(invalidName);
            assertThrows(UnsupportedOperationException.class, validator::getFirstName);
        }
    }

    @Test
    void getSecondNameInvalidInput() {
        for (String invalidName : invalidNames) {
            NameValidator validator = new NameValidator(invalidName);
            assertThrows(UnsupportedOperationException.class, validator::getLastName);
        }
    }

    @Test
    void isValidWithInvalidInput() {
        for (String invalidName : invalidNames) {
            NameValidator validator = new NameValidator(invalidName);
            assertFalse(validator.isValid());
        }
    }
}