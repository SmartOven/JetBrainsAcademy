package account.model.breachedpassword;

import account.model.Mapper;
import org.springframework.stereotype.Service;

@Service
public class BreachedPasswordMapper implements Mapper<BreachedPassword, BreachedPasswordDto> {

    @Override
    public BreachedPasswordDto mappingToDto(BreachedPassword entity) {
        BreachedPasswordDto dto = new BreachedPasswordDto();
        return mappingToDto(dto, entity);
    }

    @Override
    public BreachedPasswordDto mappingToDto(BreachedPasswordDto dto, BreachedPassword entity) {
        dto.setPassword(entity.getPassword());
        return dto;
    }

    @Override
    public BreachedPassword mappingToEntity(BreachedPasswordDto dto) {
        BreachedPassword entity = new BreachedPassword();
        return mappingToEntity(entity, dto);
    }

    @Override
    public BreachedPassword mappingToEntity(BreachedPassword entity, BreachedPasswordDto dto) {
        entity.setPassword(dto.getPassword());
        return entity;
    }
}
