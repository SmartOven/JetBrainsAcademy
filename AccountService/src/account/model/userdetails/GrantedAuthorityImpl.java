package account.model.userdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public class GrantedAuthorityImpl implements GrantedAuthority {

    private final String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}