package account.model.util;

import account.model.userdetails.UserDetailsEntity;
import lombok.Getter;

@Getter
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
