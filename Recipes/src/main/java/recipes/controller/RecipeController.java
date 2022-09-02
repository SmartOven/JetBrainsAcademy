package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import recipes.model.recipe.Recipe;
import recipes.model.recipe.RecipeDto;
import recipes.model.recipe.RecipeMapper;
import recipes.model.recipe.RecipeService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    /**
     * Finds and return the Recipe by the ID.
     * If it doesn't exist, return 404 Not found
     *
     * @param id recipe id to look for
     * @return recipeDto, that was built by recipe with given ID
     */
    @GetMapping("/{id}")
    public RecipeDto findRecipeById(@PathVariable Long id) {
        Recipe recipe = service.findById(id).orElseThrow(NoSuchElementException::new);
        return mapper.mappingToDto(recipe);
    }

    /**
     * Creates new recipe, gives it unique ID and returns that ID as JSON parameter
     *
     * @param dto recipe's dto to be saved
     * @return id of saved recipe
     */
    @PostMapping("/new")
    public Map<String, Long> createRecipe(@Valid @RequestBody RecipeDto dto) {
        Long createdId = service.save(dto).getId();
        return Map.of("id", createdId);
    }

    /**
     * Deletes the recipe with the given ID.
     * If it was deleted, returns 204 No content.
     * If recipe with given ID doesn't exist, returns 404 Not found
     *
     * @param id id of the recipe to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipeById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRecipeById(@PathVariable Long id, @Valid @RequestBody RecipeDto dto) {
        if (!service.existsById(id)) {
            throw new NoSuchElementException();
        }
        service.update(dto, id);
    }

    @GetMapping("/search")
    public List<RecipeDto> getRecipesByParams(@RequestParam Map<String, String> params) {
        if (params.containsKey("category") && params.containsKey("name") || !params.containsKey("category") && !params.containsKey("name")) {
            throw new IllegalArgumentException();
        } else if (params.containsKey("category")) {
            return mapper.mappingToDto(
                    service.findAllByCategoryIgnoreCaseOrderByDateDesc(params.get("category"))
            );
        } else {
            return mapper.mappingToDto(
                    service.findAllByNameContainingIgnoreCaseOrderByDateDesc(params.get("name"))
            );
        }
    }

    private final RecipeService service;
    private final RecipeMapper mapper;

    @Autowired
    public RecipeController(RecipeService recipeService, RecipeMapper mapper) {
        this.service = recipeService;
        this.mapper = mapper;
    }
}
