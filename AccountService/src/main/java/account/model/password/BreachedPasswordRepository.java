package account.model.password;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BreachedPasswordRepository extends JpaRepository<BreachedPassword, Long> {
    boolean existsByPassword(String password);
}
