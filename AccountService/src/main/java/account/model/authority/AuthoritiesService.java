package account.model.authority;

import account.model.authority.enums.Authority;
import account.model.authority.enums.AuthorityGroup;
import account.model.authority.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AuthoritiesService {
    public GrantedAuthorityImpl findAuthority(Authority authority) {
        return repository.findByAuthority(authority)
                .orElseThrow(() -> new NoSuchElementException("Authority doesn't exist: " + authority.name()));
    }

    public GrantedAuthorityImpl findAuthorityByRole(Role role) {
        return findAuthority(role.getAsAuthority());
    }

    public AuthoritiesService(@Autowired AuthoritiesRepository repository) {
        this.repository = repository;
        addDefaultRolesToDb();
    }

    /**
     * Added for the testing purposes to fill DB with all
     */
    void addDefaultRolesToDb() {
        List<GrantedAuthorityImpl> authorities = List.of(
                new GrantedAuthorityImpl(null, Authority.ROLE_USER, AuthorityGroup.BUSINESS),
                new GrantedAuthorityImpl(null, Authority.ROLE_ACCOUNTANT, AuthorityGroup.BUSINESS),
                new GrantedAuthorityImpl(null, Authority.ROLE_ADMINISTRATOR, AuthorityGroup.ADMINISTRATIVE),
                new GrantedAuthorityImpl(null, Authority.ROLE_AUDITOR, AuthorityGroup.BUSINESS)
        );
        for (GrantedAuthorityImpl authority : authorities) { // iterate through all the roles
            // If not presented yet - save
            if (!repository.existsByAuthority(Authority.valueOf(authority.getAuthority()))) {
                repository.save(authority);
            }
        }
    }

    private final AuthoritiesRepository repository;
}
