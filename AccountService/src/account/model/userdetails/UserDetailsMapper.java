package account.model.userdetails;

import account.model.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
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
        dto.setName(entity.getName());
        dto.setLastname(entity.getLastname());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getHashedPassword());
        dto.setAuthorities(entity.getAuthorities());
        dto.setAccountNonExpired(entity.isAccountNonExpired());
        dto.setAccountNonLocked(entity.isAccountNonLocked());
        dto.setCredentialsNonExpired(entity.isCredentialsNonExpired());
        dto.setEnabled(entity.isEnabled());
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
        List<GrantedAuthority> authorities = mapAuthorities(dto.getAuthorities()); // Mapping GrantedAuthorities

        entity.setName(dto.getName());
        entity.setLastname(dto.getLastname());
        entity.setEmail(dto.getEmail());
        entity.setHashedPassword(hashedPassword);
        entity.setAuthorities(authorities);
        entity.setAccountNonExpired(dto.isAccountNonExpired());
        entity.setAccountNonLocked(dto.isAccountNonLocked());
        entity.setCredentialsNonExpired(dto.isCredentialsNonExpired());
        entity.setEnabled(dto.isEnabled());
        return entity;
    }

    static List<GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(authority -> (GrantedAuthority) authority).collect(Collectors.toList());
    }

    public UserDetailsMapper(@Autowired PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    private final PasswordEncoder encoder;
}
