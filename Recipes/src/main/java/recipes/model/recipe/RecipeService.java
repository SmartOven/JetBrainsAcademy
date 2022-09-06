package recipes.model.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.model.CrudService;
import recipes.model.recipe.util.RecipeDto;
import recipes.model.recipe.util.RecipeMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService implements CrudService<Recipe, Long> {
    public List<Recipe> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        return repository.findById(id);
    }

    public List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category) {
        return repository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name) {
        return repository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    @Override
    public Recipe save(Recipe recipe) {
        return repository.save(recipe);
    }

    public void update(RecipeDto dto, Recipe recipe, Long id) {
        recipe = mapper.mappingToEntity(recipe, dto);
        repository.save(recipe);
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

    public RecipeService(@Autowired RecipeRepository repository, @Autowired RecipeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
}
