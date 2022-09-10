package account.model.salary;

import account.model.user.UserDetailsEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Locale;

@Service
public class UserSalaryOnPeriodMapper {

    public UserSalaryOnPeriod mappingToEntity(UserDetailsEntity user, LocalDate period, Long salary) {
        UserSalaryOnPeriod entity = new UserSalaryOnPeriod();
        return mappingToEntity(entity, user, period, salary);
    }

    public UserSalaryOnPeriod mappingToEntity(UserSalaryOnPeriod entity, UserDetailsEntity user, LocalDate period, Long salary) {
        entity.setUser(user);
        entity.setPeriod(period);
        entity.setSalary(salary);
        return entity;
    }

    public UserSalaryOnPeriodInfo mappingToInfo(UserSalaryOnPeriod entity) {
        UserSalaryOnPeriodInfo info = new UserSalaryOnPeriodInfo();
        info.setName(entity.getUser().getName());
        info.setLastname(entity.getUser().getLastname());
        info.setPeriod(localDateToString(entity.getPeriod()));
        info.setSalary(salaryToString(entity.getSalary()));
        return info;
    }

    static String localDateToString(LocalDate date) {
        String month = date.getMonth().toString().toLowerCase(Locale.ROOT);
        month = Character.toUpperCase(month.charAt(0)) + month.substring(1);

        return month + "-" + date.getYear();
    }

    static String salaryToString(Long salary) {
        long dollars = salary / 100;
        long cents = salary % 100;
        return String.format("%d dollar(s) %d cent(s)", dollars, cents);
    }
}
