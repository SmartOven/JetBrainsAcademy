package account.model.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserChangingPasswordResponse {
    private final String email;
    private final String status;
}
