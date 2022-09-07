package account.model.userdetails;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserCreationResponse {
    private final Long id;
    private final String name;
    private final String lastname;
    private final String email;

    public UserCreationResponse(UserDetailsEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.lastname = entity.getLastname();
        this.email = entity.getEmail();
    }
}
