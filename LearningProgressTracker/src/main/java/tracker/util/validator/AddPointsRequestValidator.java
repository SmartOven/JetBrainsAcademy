package tracker.util.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddPointsRequestValidator extends Validator {

    private List<Integer> additionalPoints;
    private UUID id;

    public AddPointsRequestValidator(String requestString) {
        validate(requestString);
    }

    @Override
    protected void validate(String requestString) {
        String[] requestParts = requestString.split("\\s+");

        // By default, we think that something may go wrong
        valid = false;
        additionalPoints = null;
        id = null;

        // Wrong number of arguments passed
        if (requestParts.length != 5) {
            return;
        }

        try {
            id = UUID.fromString(requestParts[0]);
        } catch (Exception e) {
            // Incorrect ID was passed
            return;
        }

        additionalPoints = new ArrayList<>();

        int p;
        for (int i = 1; i < 5; i++) {
            try {
                p = Integer.parseInt(requestParts[i]);
            } catch (NumberFormatException e) {
                // Not a number
                return;
            }

            // Additional points cannot be less than zero
            if (p < 0) {
                return;
            }

            additionalPoints.add(p);
        }

        // Everything went well
        valid = true;
    }

    public List<Integer> getAdditionalPoints() {
        ifNotValidThrow("Given request is not valid");
        return additionalPoints;
    }

    public UUID getId() {
        ifNotValidThrow("Given request is not valid");
        return id;
    }
}
