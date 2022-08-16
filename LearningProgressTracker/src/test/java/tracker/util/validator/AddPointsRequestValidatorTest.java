package tracker.util.validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AddPointsRequestValidatorTest {

    private AddPointsRequestValidator validator;
    private static UUID id;

    @BeforeAll
    static void beforeAll() {
        id = UUID.randomUUID();
    }

    @Test
    @DisplayName("isValid smoke test")
    void isValid() {
        validator = new AddPointsRequestValidator(id + " 0 0 0 0");
        assertTrue(validator.isValid());

        validator = new AddPointsRequestValidator("abc");
        assertFalse(validator.isValid());
    }

    @Test
    void validTests() {
        List<String> validRequests = List.of(
                "0 1 2 3", "10 11 12 13"
        );

        for (String validRequest : validRequests) {
            validator = new AddPointsRequestValidator(id + " " + validRequest);
            assertTrue(validator.isValid());
        }
    }

    @Test
    void invalidTests() {
        List<String> invalidRequests = List.of(
                "", "-1 -1 -1 -1", "0 0 0", "0 0 0 0 0"
        );

        for (String invalidRequest : invalidRequests) {
            validator = new AddPointsRequestValidator(id + " " + invalidRequest);
            assertFalse(validator.isValid());
        }
    }
}