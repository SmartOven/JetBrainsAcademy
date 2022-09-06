package recipes.model.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.model.CrudService;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AuthorService implements UserDetailsService, CrudService<Author, Long> {
    @Override
    public Author loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findUserByEmail(username).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Optional<Author> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Author save(Author author) {
        return repository.save(author);
    }

    @Override
    public void delete(Author author) {
        repository.delete(author);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public AuthorService(@Autowired AuthorRepository repository) {
        this.repository = repository;
    }

    private final AuthorRepository repository;
}
