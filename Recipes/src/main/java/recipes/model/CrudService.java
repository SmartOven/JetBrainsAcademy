package recipes.model;

import java.util.Optional;

public interface CrudService<Entity, ID> {
    Optional<Entity> findById(ID id);
    Entity save(Entity entity);
    void delete(Entity entity);
    void deleteById(ID id);
}
