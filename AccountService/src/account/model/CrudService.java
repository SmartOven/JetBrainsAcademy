package account.model;

import java.util.Optional;

public interface CrudService<Entity, ID> {
    Entity create(Entity entity);     // Create
    Optional<Entity> findById(ID id); // Read
    Entity update(Entity entity);     // Update
    void delete(Entity entity);       // Delete
    void deleteById(ID id);
}
