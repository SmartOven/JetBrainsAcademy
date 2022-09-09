package account.model.user;

import account.model.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserDetailsMapper implements Mapper<UserDetailsEntity, UserDetailsDto> {
    @Override
    public UserDetailsDto mappingToDto(UserDetailsEntity entity) {
        UserDetailsDto dto = new UserDetailsDto();
        return mappingToDto(dto, entity);
    }

    @Override
    public UserDetailsDto mappingToDto(UserDetailsDto dto, UserDetailsEntity entity) {
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLastname(entity.getLastname());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getHashedPassword());
        return dto;
    }

    @Override
    public UserDetailsEntity mappingToEntity(UserDetailsDto dto) {
        UserDetailsEntity entity = new UserDetailsEntity();
        return mappingToEntity(entity, dto);
    }

    @Override
    public UserDetailsEntity mappingToEntity(UserDetailsEntity entity, UserDetailsDto dto) {
        String hashedPassword = encoder.encode(dto.getPassword()); // Hashing password
        String email = dto.getEmail().toLowerCase(Locale.ROOT); // lower case for case insensitivity

        entity.setName(dto.getName());
        entity.setLastname(dto.getLastname());
        entity.setEmail(email);
        entity.setHashedPassword(hashedPassword);
        return entity;
    }

    public UserDetailsMapper(@Autowired PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    private final PasswordEncoder encoder;
}
