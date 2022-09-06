package account.model.userdetails;

import account.model.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, CrudService<UserDetailsEntity, Long> {
    /**
     * Gets UserDetailsEntity object from database by username
     * Maps it to the UserDetailsDto object and returns it
     *
     * @param username the username identifying the user whose data is required.
     * @return UserDetails by username
     * @throws UsernameNotFoundException exception when user not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsEntity entity = repository.findUserDetailsEntityByUsername(username)
                .orElseThrow(NoSuchElementException::new);
        return mapper.mappingToDto(entity);
    }

    @Override
    public UserDetailsEntity create(UserDetailsEntity entity) {
        return null;
    }

    @Override
    public Optional<UserDetailsEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public UserDetailsEntity update(UserDetailsEntity entity) {
        return null;
    }

    @Override
    public void delete(UserDetailsEntity entity) {

    }

    @Override
    public void deleteById(Long id) {

    }

    public UserDetailsServiceImpl(@Autowired UserDetailsRepository repository,
                                  @Autowired UserDetailsMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    private final UserDetailsRepository repository;
    private final UserDetailsMapper mapper;
}
