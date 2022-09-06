package account.model;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<Entity, Dto> {
    Dto mappingToDto(Entity entity);
    Dto mappingToDto(Dto dto, Entity entity);
    Entity mappingToEntity(Dto dto);
    Entity mappingToEntity(Entity entity, Dto dto);

    default List<Dto> mappingToDto(List<Entity> entitiesList) {
        return entitiesList.stream().map(this::mappingToDto).collect(Collectors.toList());
    }

    default List<Entity> mappingToEntity(List<Dto> dtoList) {
        return dtoList.stream().map(this::mappingToEntity).collect(Collectors.toList());
    }
}
