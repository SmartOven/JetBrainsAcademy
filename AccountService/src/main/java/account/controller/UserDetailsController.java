package account.controller;

import account.exception.UserExistsException;
import account.model.userdetails.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class UserDetailsController {
    @PostMapping("/auth/signup")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailsDto signUpNewUser(@Valid @RequestBody UserDetailsDto userDetailsDto) {
        // Validating unique email
        if (service.existsByEmail(userDetailsDto.getEmail())) {
            throw new UserExistsException();
        }

        UserDetailsEntity createdUser = service.create(userDetailsDto);
        return mapper.mappingToDto(createdUser);
    }

    @GetMapping("/empl/payment")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailsDto getAuthenticatedUserInfo(@AuthenticationPrincipal UserDetails details) {
        System.out.println("details username: " + details.getUsername());

        UserDetailsEntity authenticatedUser = service.findByEmail(details.getUsername())
                .orElseThrow(NoSuchElementException::new);
        System.out.println("user found: " + authenticatedUser);

        return mapper.mappingToDto(authenticatedUser);
    }

    public UserDetailsController(@Autowired UserDetailsServiceImpl service,
                                 @Autowired UserDetailsMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    private final UserDetailsServiceImpl service;
    private final UserDetailsMapper mapper;
}
