package recipes.model.recipe.util;

import org.springframework.stereotype.Service;
import recipes.model.Mapper;
import recipes.model.recipe.Recipe;

import java.time.LocalDateTime;

@Service
public class RecipeMapper implements Mapper<Recipe, RecipeDto> {
    @Override
    public RecipeDto mappingToDto(Recipe entity) {
        RecipeDto dto = new RecipeDto();
        return mappingToDto(dto, entity);
    }

    @Override
    public RecipeDto mappingToDto(RecipeDto dto, Recipe entity) {
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCategory(entity.getCategory());
        dto.setIngredients(entity.getIngredients());
        dto.setDirections(entity.getDirections());
        dto.setDate(entity.getDate());
        return dto;
    }

    @Override
    public Recipe mappingToEntity(RecipeDto dto) {
        Recipe recipe = new Recipe();
        return mappingToEntity(recipe, dto);
    }

    @Override
    public Recipe mappingToEntity(Recipe entity, RecipeDto dto) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCategory(dto.getCategory());
        entity.setIngredients(dto.getIngredients());
        entity.setDirections(dto.getDirections());

        // Custom date setting
        entity.setDate(LocalDateTime.now());
        return entity;
    }
}
