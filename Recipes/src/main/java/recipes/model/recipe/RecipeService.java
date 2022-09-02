package recipes.model.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.model.CrudService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService implements CrudService<Recipe, Long> {
    @Override
    public Optional<Recipe> findById(Long id) {
        return repository.findById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category) {
        return repository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name) {
        return repository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public long count() {
        return repository.count();
    }

    @Override
    public Recipe save(Recipe recipe) {
        return repository.save(recipe);
    }

    public Recipe save(RecipeDto dto) {
        Recipe recipe = mapper.mappingToEntity(dto);
        recipe.setDate(LocalDateTime.now());
        return repository.save(recipe);
    }

    public Recipe update(RecipeDto dto, Long id) {
        Recipe recipe = mapper.mappingToEntity(dto);
        recipe.setDate(LocalDateTime.now());
        recipe.setId(id);
        return repository.save(recipe);
    }

    @Override
    public void delete(Recipe recipe) {
        repository.delete(recipe);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private final RecipeRepository repository;
    private final RecipeMapper mapper;

    @Autowired
    public RecipeService(RecipeRepository repository, RecipeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
}
