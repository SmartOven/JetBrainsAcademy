package recipes.model.author.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.model.Mapper;
import recipes.model.author.Author;

@Service
public class AuthorMapper implements Mapper<Author, AuthorDto> {
    @Override
    public AuthorDto mappingToDto(Author entity) {
        AuthorDto dto = new AuthorDto();
        return mappingToDto(dto, entity);
    }

    @Override
    public AuthorDto mappingToDto(AuthorDto dto, Author entity) {
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getHashedPassword());
        return dto;
    }

    @Override
    public Author mappingToEntity(AuthorDto dto) {
        Author entity = new Author();
        return mappingToEntity(entity, dto);
    }

    @Override
    public Author mappingToEntity(Author entity, AuthorDto dto) {
        entity.setEmail(dto.getEmail());
        entity.setHashedPassword(encoder.encode(dto.getPassword()));
        return entity;
    }

    public AuthorMapper(@Autowired PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    private final PasswordEncoder encoder;
}
