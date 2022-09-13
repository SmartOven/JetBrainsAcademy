package account.model.user.login;

import account.model.user.UserDetailsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class LoginAttemptService {

    public void createForUser(UserDetailsEntity user) {
        repository.save(buildDefaultEntity(user));
    }

    public void incrementLoginAttempts(LoginAttempt loginAttempt) {
        loginAttempt.setAttemptsCount(loginAttempt.getAttemptsCount() + 1);
        repository.save(loginAttempt);
    }

    public void resetLoginAttemptsByUser(UserDetailsEntity user) {
        if (!user.isAccountNonLocked()) {
            System.out.println("Account locked, resetting impossible");
            return;
        }
        LoginAttempt entity = getLoginAttemptsByUser(user);
        entity.setAttemptsCount(defaultAttemptsCount);
        repository.save(entity);
    }

    public void deleteByUser(UserDetailsEntity user) {
        repository.deleteByUser(user);
    }

    public LoginAttempt getLoginAttemptsByUser(UserDetailsEntity user) {
        return repository.findByUser(user)
//                .orElse(buildDefaultEntity(user)) // Can be built and saved (inserting new instead of updating)
                .orElseThrow(() -> new NoSuchElementException("User doesn't have login attempts record"));
    }

    LoginAttempt buildDefaultEntity(UserDetailsEntity user) {
        LoginAttempt entity = new LoginAttempt();
        entity.setUser(user);
        entity.setAttemptsCount(defaultAttemptsCount);
        return entity;
    }

    private static final Long defaultAttemptsCount = 0L;

    public LoginAttemptService(@Autowired LoginAttemptRepository repository) {
        this.repository = repository;
    }
    private final LoginAttemptRepository repository;
}
