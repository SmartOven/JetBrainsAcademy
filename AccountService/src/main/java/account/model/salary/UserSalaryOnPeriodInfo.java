package account.model.salary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSalaryOnPeriodInfo {
    private String name;
    private String lastname;
    private String period;
    private String salary;
}
