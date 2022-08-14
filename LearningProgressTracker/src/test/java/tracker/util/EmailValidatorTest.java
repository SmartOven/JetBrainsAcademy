package tracker.util;

import org.junit.jupiter.api.Test;
import tracker.util.validator.EmailValidator;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    final static String[] validEmails = {
            "email@example.com", "y.pan-01@cloud.yandex.ru", "1@1.1", "abc123@zz09.zz09"
    };
    final static String[] invalidEmails = {
            // Empty name, empty site, empty domain, empty site name, no @, too long domain, wrong name of site
            "@yandex.ru", "email@", "email@yandex", "email@.ru", "yandex.ru", "email@yandex.longdomainru", "email@..com"
    };

    @Test
    void getEmailValidInput() {
        for (String validEmail : validEmails) {
            EmailValidator validator = new EmailValidator(validEmail);
            assertEquals(validEmail, validator.getEmail());
        }
    }

    @Test
    void isValidWithValidInput() {
        for (String validEmail : validEmails) {
            EmailValidator validator = new EmailValidator(validEmail);
            assertTrue(validator.isValid());
        }
    }

    @Test
    void getEmailInvalidInput() {
        for (String invalidEmail : invalidEmails) {
            EmailValidator validator = new EmailValidator(invalidEmail);
            assertThrows(UnsupportedOperationException.class, validator::getEmail);
        }
    }

    @Test
    void isValidWithInvalidInput() {
        for (String invalidEmail : invalidEmails) {
            EmailValidator validator = new EmailValidator(invalidEmail);
            assertFalse(validator.isValid());
        }
    }
}