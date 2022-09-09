package account.model.salary;

import account.model.user.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface UserSalaryOnPeriodRepository extends JpaRepository<UserSalaryOnPeriod, Long> {
    boolean existsByUserAndPeriod(UserDetailsEntity user, LocalDate period);
    Optional<UserSalaryOnPeriod> findByUserAndPeriod(UserDetailsEntity user, LocalDate period);
}
