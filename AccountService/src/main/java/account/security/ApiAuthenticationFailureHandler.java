package account.security;

import account.model.logger.Log;
import account.model.logger.LogEvent;
import account.model.logger.Logger;
import account.model.user.UserDetailsEntity;
import account.model.user.UserDetailsServiceImpl;
import account.model.user.login.LoginAttempt;
import account.model.user.login.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.NoSuchElementException;

@Component
public class ApiAuthenticationFailureHandler implements
        ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private HttpServletRequest request;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String email = ((String) event.getAuthentication().getPrincipal()).toLowerCase(Locale.ROOT);
        String path = request.getRequestURI();
        UserDetailsEntity user;
        try {
            user = userDetailsService.getByEmail(email);
        } catch (NoSuchElementException e) {
            logger.log(Log.builder()
                    .date(LocalDateTime.now())
                    .action(LogEvent.LOGIN_FAILED)
                    .subject(email)
                    .object(path)
                    .path(path)
                    .build()
            );
            return;
        }

        // Do nothing if account is locked
        if (!user.isAccountNonLocked()) {
            System.out.println("Account locked, updating attempts unnecessary");
            return;
        }

        LoginAttempt loginAttempt = loginAttemptService.getLoginAttemptsByUser(user);
        Long attemptsCount = loginAttempt.getAttemptsCount();

        logger.log(Log.builder()
                .date(LocalDateTime.now())
                .action(LogEvent.LOGIN_FAILED)
                .subject(email)
                .object(path)
                .path(path)
                .build()
        );
        if (attemptsCount == 4) {
            // 5th attempt is right now, at 5th failed attempts user is being blocked
            userDetailsService.lockUserByEmail(email);
            logger.log(Log.builder()
                    .date(LocalDateTime.now())
                    .action(LogEvent.BRUTE_FORCE)
                    .subject(email)
                    .object(path)
                    .path(path)
                    .build()
            );
            logger.log(Log.builder()
                    .date(LocalDateTime.now())
                    .action(LogEvent.LOCK_USER)
                    .subject(email)
                    .object("Lock user " + email)
                    .path(path)
                    .build()
            );
        }

        loginAttemptService.incrementLoginAttempts(loginAttempt);
    }

    public ApiAuthenticationFailureHandler(@Autowired LoginAttemptService loginAttemptService,
                                           @Autowired UserDetailsServiceImpl userDetailsService,
                                           @Autowired Logger logger) {
        this.loginAttemptService = loginAttemptService;
        this.userDetailsService = userDetailsService;
        this.logger = logger;
    }

    private final LoginAttemptService loginAttemptService;
    private final UserDetailsServiceImpl userDetailsService;
    private final Logger logger;
}