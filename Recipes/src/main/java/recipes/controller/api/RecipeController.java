package recipes.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import recipes.controller.ForbiddenException;
import recipes.model.recipe.Recipe;
import recipes.model.recipe.RecipeService;
import recipes.model.recipe.util.RecipeCreationResponse;
import recipes.model.recipe.util.RecipeDto;
import recipes.model.recipe.util.RecipeMapper;
import recipes.model.author.Author;
import recipes.model.author.AuthorService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    @GetMapping("/")
    public List<RecipeDto> findAll() {
        return mapper.mappingToDto(
                recipeService.findAll()
        );
    }

    /**
     * Finds and return the Recipe by the ID.
     * If it doesn't exist, return 404 Not found
     *
     * @param id recipe id to look for
     * @return recipeDto, that was built by recipe with given ID
     */
    @GetMapping("/{id}")
    public RecipeDto findRecipeById(@PathVariable Long id) {
        Recipe recipe = recipeService.findById(id).orElseThrow(NoSuchElementException::new);
        return mapper.mappingToDto(recipe);
    }

    /**
     * Creates new recipe, gives it unique ID and returns that ID as JSON parameter
     *
     * @param dto recipe's dto to be saved
     * @return id of saved recipe
     */
    @PostMapping("/new")
    public RecipeCreationResponse createRecipe(@Valid @RequestBody RecipeDto dto,
                                               @AuthenticationPrincipal UserDetails details) {
        Recipe recipe = mapper.mappingToEntity(dto);
        Author author = authorService.loadUserByUsername(details.getUsername());
        recipe.setAuthor(author);

        Long createdRecipeId = recipeService.save(recipe).getId();
        return new RecipeCreationResponse(createdRecipeId);
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
    public void deleteRecipeById(@PathVariable Long id,
                                 @AuthenticationPrincipal UserDetails details) {
        Author author = authorService.loadUserByUsername(details.getUsername());
        Recipe recipe = recipeService.findById(id).orElseThrow(NoSuchElementException::new);
        if (!recipe.getAuthor().equals(author)) {
            throw new ForbiddenException("Authorized user is not the author of that recipe");
        }
        recipeService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRecipeById(@PathVariable Long id,
                                 @Valid @RequestBody RecipeDto dto,
                                 @AuthenticationPrincipal UserDetails details) {
        Author author = authorService.loadUserByUsername(details.getUsername());
        Recipe recipe = recipeService.findById(id).orElseThrow(NoSuchElementException::new);
        if (!recipe.getAuthor().equals(author)) {
            throw new ForbiddenException("Authorized user is not the author of that recipe");
        }
        recipeService.update(dto, recipe, id);
    }

    @GetMapping("/search")
    public List<RecipeDto> getRecipesByParams(@RequestParam Map<String, String> params) {
        if (params.containsKey("category") && params.containsKey("name") || !params.containsKey("category") && !params.containsKey("name")) {
            throw new IllegalArgumentException();
        } else if (params.containsKey("category")) {
            return mapper.mappingToDto(
                    recipeService.findAllByCategoryIgnoreCaseOrderByDateDesc(params.get("category"))
            );
        } else {
            return mapper.mappingToDto(
                    recipeService.findAllByNameContainingIgnoreCaseOrderByDateDesc(params.get("name"))
            );
        }
    }

    private final RecipeService recipeService;
    private final AuthorService authorService;
    private final RecipeMapper mapper;

    public RecipeController(@Autowired RecipeService recipeService,
                            @Autowired AuthorService authorService,
                            @Autowired RecipeMapper mapper) {
        this.recipeService = recipeService;
        this.authorService = authorService;
        this.mapper = mapper;
    }
}
