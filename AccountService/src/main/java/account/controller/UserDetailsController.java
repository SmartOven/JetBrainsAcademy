package account.controller;

import account.exception.DataManagementException;
import account.model.user.UserDetailsMapper;
import account.model.user.*;
import account.model.util.NewPasswordDto;
import account.model.util.UserChangingPasswordResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        UserDetailsEntity createdUser = service.create(userDetailsDto);
        return mapper.mappingToDto(createdUser);
    }

//    @GetMapping("/empl/payment")
//    @ResponseStatus(HttpStatus.OK)
//    public UserDetailsDto getAuthenticatedUserInfo(@AuthenticationPrincipal UserDetails details) {
//        UserDetailsEntity authenticatedUser = service.getByEmail(details.getUsername());
//        return mapper.mappingToDto(authenticatedUser);
//    }

    @PostMapping("/auth/changepass")
    @ResponseStatus(HttpStatus.OK)
    public UserChangingPasswordResponse changeUserPassword(@Valid @RequestBody NewPasswordDto newPasswordDto,
                                                           BindingResult errors,
                                                           @AuthenticationPrincipal UserDetails details) {
        if (errors.hasErrors()) { // validate errors handling
            // throw new UserManagementException(errors);
            // UPD: tests are bad, so they ask for exactly this message in the response,
            //      instead of the regular one
            throw new DataManagementException("Password length must be 12 chars minimum!");
        }

        String email = details.getUsername();
        String newPassword = newPasswordDto.getNew_password();
        String status = "The password has been updated successfully";

        service.updateUserPasswordByEmail(email, newPassword);
        return new UserChangingPasswordResponse(email, status);
    }

    public UserDetailsController(@Autowired UserDetailsServiceImpl service,
                                 @Autowired UserDetailsMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    private final UserDetailsServiceImpl service;
    private final UserDetailsMapper mapper;
}
