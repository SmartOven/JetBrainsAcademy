package account.model.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        return findByEmail(username)
                .orElseThrow(NoSuchElementException::new);
    }

    public UserDetailsEntity create(UserDetailsDto dto) {
        UserDetailsEntity entity = mapper.mappingToEntity(dto);
        return repository.save(entity);
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

    public void updateByEntity(UserDetailsEntity entity) {
        repository.save(entity);
    }

    public void update(UserDetailsDto dto) {
        UserDetailsEntity entity = mapper.mappingToEntity(dto);
        repository.save(entity);
    }

//    public void update(UserDetailsEntity entity, UserDetailsDto dto) {
//        entity = mapper.mappingToEntity(entity, dto);
//        updateByEntity(entity);
//    }

    public void delete(UserDetailsDto dto) {
        UserDetailsEntity entity = mapper.mappingToEntity(dto);
        repository.delete(entity);
    }

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
