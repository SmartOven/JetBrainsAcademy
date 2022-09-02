package recipes.model;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<E, D> {
    D mappingToDto(E entity);
    D mappingToDto(D dto, E entity);
    E mappingToEntity(D dto);
    E mappingToEntity(E entity, D dto);

    default List<D> mappingToDto(List<E> entitiesList) {
        return entitiesList.stream().map(this::mappingToDto).collect(Collectors.toList());
    }

    default List<E> mappingToEntity(List<D> dtoList) {
        return dtoList.stream().map(this::mappingToEntity).collect(Collectors.toList());
    }
}
