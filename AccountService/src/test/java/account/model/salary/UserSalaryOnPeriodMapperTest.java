package account.model.salary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserSalaryOnPeriodMapperTest {

    @ParameterizedTest
    @CsvSource({
            "1, January",
            "2, February",
            "3, March"
    })
    void localDateToString(int monthNumber, String month) {
        String convertedMonth = UserSalaryOnPeriodMapper.
                localDateToString(LocalDate.of(2022, monthNumber, 1)).
                split("-")[0];
        assertEquals(month, convertedMonth);
    }
}