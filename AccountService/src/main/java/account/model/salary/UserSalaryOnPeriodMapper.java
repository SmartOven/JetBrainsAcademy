package account.model.salary;

import account.model.user.UserDetailsEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserSalaryOnPeriodMapper {

    public UserSalaryOnPeriod mappingToEntity(UserDetailsEntity user, LocalDate period, Long salary) {
        UserSalaryOnPeriod entity = new UserSalaryOnPeriod();
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
        String month = String.valueOf(date.getMonth().getValue());
        if (month.length() == 1) {
            month = "0" + month;
        }
        return month + "-" + date.getYear();
    }

    static String salaryToString(Long salary) {
        long dollars = salary / 100;
        long cents = salary % 100;
        return String.format("%d dollar(s) %d cent(s)", dollars, cents);
    }
}
