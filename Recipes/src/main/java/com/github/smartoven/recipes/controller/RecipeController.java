package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import recipes.model.pojo.IdWrapper;
import recipes.model.pojo.RecipeInfo;
import recipes.model.recipe.Recipe;
import recipes.model.recipe.RecipeRepository;

import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    @GetMapping("/{id}")
    public RecipeInfo getRecipe(@PathVariable Long id) {
        Recipe recipe = repository.findById(id).orElseThrow(NoSuchElementException::new);
        return new RecipeInfo(recipe);
    }

    @PostMapping("/new")
    public IdWrapper createRecipe(@RequestBody Recipe recipe) {
        Long savedId = repository.save(recipe).getId();
        return new IdWrapper(savedId);
    }

    private final RecipeRepository repository;

    public RecipeController(@Autowired RecipeRepository repository) {
        this.repository = repository;
    }
}
