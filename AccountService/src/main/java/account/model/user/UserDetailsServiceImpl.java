package account.model.user;

import account.exception.DataManagementException;
import account.model.authority.*;
import account.model.authority.enums.Authority;
import account.model.authority.enums.AuthorityGroup;
import account.model.authority.enums.Role;
import account.model.password.BreachedPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Using email as username
        // Email is case-insensitive
        return getByEmail(username);
    }

    public UserDetailsEntity create(UserDetailsDto dto) {
        validateEmailUniqueness(dto.getEmail());
        validatePasswordStrength(dto.getPassword());

        UserDetailsEntity entity = mapper.mappingToEntity(dto);
        grantDefaultRole(entity);

        return repository.save(entity);
    }

    public void updateUserPasswordByEmail(String email, String newPassword) {
        validatePasswordStrength(newPassword);

        UserDetailsEntity user = getByEmail(email);

        // Check that new password is different to the old one
        validatePasswordDifference(newPassword, user.getHashedPassword());

        // Set new password
        user.setHashedPassword(encoder.encode(newPassword));

        repository.save(user);
    }

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email.toLowerCase(Locale.ROOT));
    }

    public Optional<UserDetailsEntity> findByEmail(String email) {
        return repository.findByEmail(email.toLowerCase(Locale.ROOT));
    }

    public UserDetailsEntity getByEmail(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found!"));
    }

    public UserDetailsDto getByEmailAsDto(String email) {
        return mapper.mappingToDto(getByEmail(email));
    }

    public void deleteByEmail(String email) {
        UserDetailsEntity user = repository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found!"));
        if (userHasAuthority(user, Authority.ROLE_ADMINISTRATOR.name())) {
            throw new DataManagementException("Can't remove ADMINISTRATOR role!");
        }
        repository.deleteByEmail(email);
    }

    public void grantRoleToUserByEmail(String email, Role role) {
        GrantedAuthorityImpl authority = authoritiesService.findAuthorityByRole(role);
        UserDetailsEntity user = getByEmail(email);

        // Checking that the role has the same group as other user roles
        validateUserHasOnlyOneGroupRoles(user, role);

        // Adding authority
        user.getAuthorities().add(authority);
        repository.save(user);
    }

    public void removeRoleFromUserByEmail(String email, Role role) {
        GrantedAuthorityImpl authority = authoritiesService.findAuthorityByRole(role);
        UserDetailsEntity user = getByEmail(email);

        // Checking that authority can be removed
        validateRoleIsRemovable(role);
        validateUserHasRemovableRole(user, role);
        validateUserHasOtherRoles(user);

        // Removing authority
        user.getAuthorities().remove(authority);
        repository.save(user);
    }

    void validateEmailUniqueness(String email) {
        if (existsByEmail(email)) {
            throw new DataManagementException("User exist!");
        }
    }

    void validatePasswordStrength(String password) {
        if (breachedPasswordService.isBreached(password)) {
            throw new DataManagementException("The password is in the hacker's database!");
        }
    }

    void validatePasswordDifference(String newPassword, String oldHashedPassword) {
        if (encoder.matches(newPassword, oldHashedPassword)) {
            throw new DataManagementException("The passwords must be different!");
        }
    }

    void validateRoleIsRemovable(Role role) {
        if (role.equals(Role.ADMINISTRATOR)) {
            throw new DataManagementException("Can't remove ADMINISTRATOR role!");
        }
    }

    void validateUserHasRemovableRole(UserDetailsEntity user, Role role) {
        if (!userHasAuthority(user, "ROLE_" + role.name())) {
            throw new DataManagementException("The user does not have a role!");
        }
    }

    void validateUserHasOtherRoles(UserDetailsEntity user) {
        if (user.getAuthorities().size() < 2) {
            throw new DataManagementException("The user must have at least one role!");
        }
    }

    void validateUserHasOnlyOneGroupRoles(UserDetailsEntity user, Role role) {
        AuthorityGroup userGroup = user.getAuthorities().get(0).getAuthorityGroup();
        AuthorityGroup roleGroup = authoritiesService.findAuthorityByRole(role).getAuthorityGroup();
        if (!roleGroup.equals(userGroup)) {
            throw new DataManagementException("The user cannot combine administrative and business roles!");
        }
    }

    void grantDefaultRole(UserDetailsEntity entity) {
        if (repository.count() == 0) {
            entity.getAuthorities().add(
                    authoritiesService.findAuthorityByRole(Role.ADMINISTRATOR)
            );
        } else {
            entity.getAuthorities().add(
                    authoritiesService.findAuthorityByRole(Role.USER)
            );
        }
    }

    boolean userHasAuthority(UserDetailsEntity user, String authorityName) {
        for (GrantedAuthorityImpl userAuthority : user.getAuthorities()) {
            if (userAuthority.getAuthority().equals(authorityName)) {
                return true;
            }
        }
        return false;
    }

    public UserDetailsServiceImpl(@Autowired UserDetailsRepository repository,
                                  @Autowired UserDetailsMapper mapper,
                                  @Autowired BreachedPasswordService breachedPasswordService,
                                  @Autowired AuthoritiesService authoritiesService,
                                  @Autowired PasswordEncoder encoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.breachedPasswordService = breachedPasswordService;
        this.authoritiesService = authoritiesService;
        this.encoder = encoder;
    }

    private final UserDetailsRepository repository;
    private final UserDetailsMapper mapper;
    private final BreachedPasswordService breachedPasswordService;
    private final AuthoritiesService authoritiesService;
    private final PasswordEncoder encoder;

    public List<UserDetailsDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::mappingToDto)
                .collect(Collectors.toList());
    }
}
