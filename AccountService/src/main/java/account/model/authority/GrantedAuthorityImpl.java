package account.model.authority;

import account.model.authority.enums.Authority;
import account.model.authority.enums.AuthorityGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrantedAuthorityImpl implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "authority", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Column(name = "authority_group", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityGroup authorityGroup;

    @Override
    public String getAuthority() {
        return authority.name();
    }
}
