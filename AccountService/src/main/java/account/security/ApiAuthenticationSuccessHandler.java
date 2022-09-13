package account.security;

import account.model.user.UserDetailsEntity;
import account.model.user.UserDetailsServiceImpl;
import account.model.user.login.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ApiAuthenticationSuccessHandler implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication auth = event.getAuthentication();
        if (auth == null) {
            return;
        }
        String email = auth.getName();
        UserDetailsEntity user = userDetailsService.getByEmail(email);
        loginAttemptService.resetLoginAttemptsByUser(user);
    }

    public ApiAuthenticationSuccessHandler(@Autowired LoginAttemptService loginAttemptService,
                                           @Autowired UserDetailsServiceImpl userDetailsService) {
        this.loginAttemptService = loginAttemptService;
        this.userDetailsService = userDetailsService;
    }

    private final LoginAttemptService loginAttemptService;
    private final UserDetailsServiceImpl userDetailsService;
}