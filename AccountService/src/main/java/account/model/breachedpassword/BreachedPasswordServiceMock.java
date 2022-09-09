package account.model.breachedpassword;

import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Used for testing purposes with hardcoded immutable breached passwords set
 */
@Service
public class BreachedPasswordServiceMock {
    public boolean isBreached(String password) {
        return breachedPasswordsSet.contains(password);
    }

    private final Set<String> breachedPasswordsSet = Set.of(
            "PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember"
    );
}
