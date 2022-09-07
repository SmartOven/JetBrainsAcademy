package account.model.userdetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDetailsDto {
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String lastname;

    @Pattern(regexp = ".+@acme\\.com", message = "Email should be valid")
    private String email;

    @NotEmpty
    private String password;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
