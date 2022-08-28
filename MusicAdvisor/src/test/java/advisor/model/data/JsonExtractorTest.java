package advisor.model.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonExtractorTest {

    private static final String preMessage = "Error: ";
    private static final String noErrorMessage = "No error message specified";

    @Test
    void getErrorMessageWithMessage() {
        String errorMessage = "my message";
        JsonObject jsonWithError = ServerData.getJsonObjectWithError(errorMessage);
        assertEquals(preMessage + errorMessage, JsonExtractor.getErrorMessageIfPresent(jsonWithError));
    }

    @Test
    void getErrorMessageWithoutMessage() {
        JsonObject jsonWithErrorWithoutErrorMessage = JsonParser.parseString("{\"error\" : {}}").getAsJsonObject();
        assertEquals(preMessage + noErrorMessage, JsonExtractor.getErrorMessageIfPresent(jsonWithErrorWithoutErrorMessage));
    }
}