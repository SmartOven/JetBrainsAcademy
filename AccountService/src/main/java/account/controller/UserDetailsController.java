package account.controller;

import account.exception.DataManagementException;
import account.model.logger.Log;
import account.model.logger.LogEvent;
import account.model.logger.Logger;
import account.model.user.UserDetailsDto;
import account.model.user.UserDetailsEntity;
import account.model.user.UserDetailsMapper;
import account.model.user.UserDetailsServiceImpl;
import account.model.user.login.LoginAttemptService;
import account.model.util.NewPasswordDto;
import account.model.util.UserChangingPasswordResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Locale;

@RestController
@RequestMapping("/api")
public class UserDetailsController {
    @PostMapping("/auth/signup")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailsDto signUpNewUser(@Valid @RequestBody UserDetailsDto userDetailsDto,
                                        BindingResult errors) {
        if (errors.hasErrors()) { // validate errors handling
            throw new DataManagementException(errors);
        }
        UserDetailsEntity createdUser = userDetailsService.create(userDetailsDto);

        // Creating loginAttempt record for the user
        loginAttemptService.createForUser(createdUser);

        // Logging performed action
        logger.log(Log.builder()
                .date(LocalDateTime.now())
                .action(LogEvent.CREATE_USER)
                .subject("Anonymous")
                .object(createdUser.getEmail())
                .path("/api/auth/signup")
                .build()
        );

        return mapper.mappingToDto(createdUser);
    }

    @PostMapping("/auth/changepass")
    @ResponseStatus(HttpStatus.OK)
    public UserChangingPasswordResponse changeUserPassword(@Valid @RequestBody NewPasswordDto newPasswordDto,
                                                           BindingResult errors,
                                                           @AuthenticationPrincipal UserDetails details) {
        if (errors.hasErrors()) { // validate errors handling
            throw new DataManagementException("Password length must be 12 chars minimum!");
        }

        String email = details.getUsername().toLowerCase(Locale.ROOT);
        String newPassword = newPasswordDto.getNew_password();
        String status = "The password has been updated successfully";

        userDetailsService.updateUserPasswordByEmail(email, newPassword);

        logger.log(Log.builder()
                .date(LocalDateTime.now())
                .action(LogEvent.CHANGE_PASSWORD)
                .subject(details.getUsername())
                .object(details.getUsername())
                .path("/api/auth/changepass")
                .build()
        );
        return new UserChangingPasswordResponse(email, status);
    }

    public UserDetailsController(@Autowired UserDetailsServiceImpl userDetailsService,
                                 @Autowired LoginAttemptService loginAttemptService,
                                 @Autowired UserDetailsMapper mapper,
                                 @Autowired Logger logger) {
        this.userDetailsService = userDetailsService;
        this.loginAttemptService = loginAttemptService;
        this.mapper = mapper;
        this.logger = logger;
    }

    private final UserDetailsServiceImpl userDetailsService;
    private final LoginAttemptService loginAttemptService;
    private final UserDetailsMapper mapper;
    private final Logger logger;
}
