package account.model.userdetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

/**
 * DTO for UserDetailsEntity
 * Implementation of UserDetails
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto implements UserDetails {

    //    @NotBlank(message = "Name shouldn't be blank")
    @NotEmpty
    private String name;

    //    @NotBlank(message = "Lastname shouldn't be blank")
    @NotEmpty
    private String lastname;

    @Pattern(regexp = ".+@acme\\.com", message = "Email should be valid")
    private String email;

    @JsonIgnore
    @NotEmpty
//    @NotBlank(message = "Password shouldn't be blank")
//    @Size(min = 8, message = "Password size should be at least 8 characters")
    private String password;

    private List<GrantedAuthority> authorities;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
