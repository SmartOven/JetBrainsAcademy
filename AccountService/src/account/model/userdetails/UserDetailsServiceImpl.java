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
        return repository.save(entity);
    }

    @Override
    public Optional<UserDetailsEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public UserDetailsEntity update(UserDetailsEntity entity) {
        return repository.save(entity);
    }

    public void update(UserDetailsEntity entity, UserDetailsDto dto) {
        entity = mapper.mappingToEntity(entity, dto);
        update(entity);
    }

    @Override
    public void delete(UserDetailsEntity entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public UserDetailsServiceImpl(@Autowired UserDetailsRepository repository,
                                  @Autowired UserDetailsMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    private final UserDetailsRepository repository;
    private final UserDetailsMapper mapper;
}
