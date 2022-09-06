package recipes.model.recipe.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "category is required")
    private String category;

    private LocalDateTime date;

    @NotNull
    @Size(min = 1, message = "At least 1 ingredient is required")
    private List<String> ingredients;

    @NotNull
    @Size(min = 1, message = "At least 1 direction is required")
    private List<String> directions;
}
