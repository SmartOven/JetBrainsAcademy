package account.model.authority;

import account.model.authority.enums.Authority;
import account.model.authority.enums.Role;
import org.springframework.stereotype.Service;

@Service
public class AuthorityMapper {
    public Authority mappingToAuthority(Role role) {
        String authorityName = "ROLE_" + role.name();
        return Authority.valueOf(authorityName);
    }
}
