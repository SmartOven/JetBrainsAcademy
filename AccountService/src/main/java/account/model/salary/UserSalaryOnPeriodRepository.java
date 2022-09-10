package account.model.salary;

import account.model.user.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSalaryOnPeriodRepository extends JpaRepository<UserSalaryOnPeriod, Long> {
    boolean existsByUserAndPeriod(UserDetailsEntity user, LocalDate period);

    Optional<UserSalaryOnPeriod> findByUserAndPeriod(UserDetailsEntity user, LocalDate period);

    List<UserSalaryOnPeriod> findAllByUser(UserDetailsEntity user);
}
