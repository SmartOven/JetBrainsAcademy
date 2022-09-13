package account.model.user;

import account.model.Mapper;
import account.model.authority.GrantedAuthorityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

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
        dto.setRoles(entity.getAuthorities()
                .stream()
                .map(GrantedAuthorityImpl::getAuthority)
                .sorted()
                .collect(Collectors.toList())
        );
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
        entity.setAuthorities(new ArrayList<>());
        return entity;
    }

    public UserDetailsMapper(@Autowired PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    private final PasswordEncoder encoder;
}
