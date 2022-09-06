package recipes.model.author.util;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthorDto {
    @Pattern(regexp = ".+@.+\\..+", message = "email has not valid format")
    private String email;

    @NotBlank(message = "password should not be blank")
    @Size(min = 8, message = "password size should be at least 8 characters length")
    private String password;
}
