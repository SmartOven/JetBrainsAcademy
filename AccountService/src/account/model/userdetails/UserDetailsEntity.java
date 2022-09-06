package account.model.userdetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USER_DETAILS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    @ElementCollection
    private List<GrantedAuthority> authorities;

    @Column(name = "account_non_expired", columnDefinition = "boolean default false")
    private boolean accountNonExpired;

    @Column(name = "account_non_locked", columnDefinition = "boolean default false")
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired", columnDefinition = "boolean default false")
    private boolean credentialsNonExpired;

    @Column(name = "enabled", columnDefinition = "boolean default false")
    private boolean enabled;
}
