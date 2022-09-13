package account.model.user.login;

import account.model.user.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {
    Optional<LoginAttempt> findByUser(UserDetailsEntity user);
    void deleteByUser(UserDetailsEntity user);
}
