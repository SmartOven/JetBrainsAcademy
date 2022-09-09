package account.model.userdetails;

import account.exception.UserManagementException;
import account.model.breachedpassword.BreachedPasswordService;
import account.model.breachedpassword.BreachedPasswordServiceMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public Optional<UserDetailsEntity> findById(Long id) {
        return repository.findById(id);
    }

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email.toLowerCase(Locale.ROOT));
    }

    public Optional<UserDetailsEntity> findByEmail(String email) {
        return repository.findByEmail(email.toLowerCase(Locale.ROOT));
    }

    public UserDetailsEntity getByEmail(String email) {
        return findByEmail(email)
                .orElseThrow(NoSuchElementException::new);
    }

    public void updateByEntity(UserDetailsEntity entity) {
        repository.save(entity);
    }

    public void update(UserDetailsDto dto) {
        UserDetailsEntity entity = mapper.mappingToEntity(dto);
        repository.save(entity);
    }

    public void delete(UserDetailsDto dto) {
        UserDetailsEntity entity = mapper.mappingToEntity(dto);
        repository.delete(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    void validateEmailUniqueness(String email) {
        if (existsByEmail(email)) {
            throw new UserManagementException("User exist!");
        }
    }
    void validatePasswordStrength(String password) {
        if (breachedPasswordService.isBreached(password)) {
            throw new UserManagementException("The password is in the hacker's database!");
        }
    }

    void validatePasswordDifference(String newPassword, String oldHashedPassword) {
        if (encoder.matches(newPassword, oldHashedPassword)) {
            throw new UserManagementException("The passwords must be different!");
        }
    }
    public UserDetailsServiceImpl(@Autowired UserDetailsRepository repository,
                                  @Autowired UserDetailsMapper mapper,
//                                  @Autowired BreachedPasswordService breachedPasswordService,
                                  @Autowired BreachedPasswordServiceMock breachedPasswordService,
                                  @Autowired PasswordEncoder encoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.breachedPasswordService = breachedPasswordService;
        this.encoder = encoder;
    }

    private final UserDetailsRepository repository;
    private final UserDetailsMapper mapper;
//    private final BreachedPasswordService breachedPasswordService;
    private final BreachedPasswordServiceMock breachedPasswordService;
    private final PasswordEncoder encoder;
}
