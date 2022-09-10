package account.model.authority;

import account.model.authority.enums.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthoritiesRepository extends JpaRepository<GrantedAuthorityImpl, Long> {
    Optional<GrantedAuthorityImpl> findByAuthority(Authority authority);

    boolean existsByAuthority(Authority authority);
}
