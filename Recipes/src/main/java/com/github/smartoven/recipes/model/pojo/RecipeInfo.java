package recipes.model.pojo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import recipes.model.recipe.Recipe;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RecipeInfo {
    private final String name;
    private final String description;
    private final List<String> ingredients;
    private final List<String> directions;

    public RecipeInfo(Recipe recipe) {
        name = recipe.getName();
        description = recipe.getDescription();
        ingredients = recipe.getIngredients();
        directions = recipe.getDirections();
    }
}
