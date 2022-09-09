package account.model.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BreachedPasswordService {

    public boolean isBreached(String password) {
        return repository.existsByPassword(password);
    }

    public int createAllDistinct(List<String> breachedPasswordStringList) {
        List<BreachedPassword> newBreachedPasswordsList = breachedPasswordStringList.stream()
                .map(passwordString -> {
                    BreachedPassword entity = new BreachedPassword();
                    entity.setPassword(passwordString);
                    return entity;
                })
                .filter(entity -> !repository.existsByPassword(entity.getPassword()))
                .collect(Collectors.toList());
        return repository.saveAll(newBreachedPasswordsList).size();
    }

    public List<BreachedPasswordDto> findAll() {
        return mapper.mappingToDto(repository.findAll());
    }

    public BreachedPasswordService(@Autowired BreachedPasswordRepository repository,
                                   @Autowired BreachedPasswordMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    private final BreachedPasswordRepository repository;
    private final BreachedPasswordMapper mapper;
}
