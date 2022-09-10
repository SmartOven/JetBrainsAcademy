package account.controller;

import account.model.authority.enums.Role;
import account.model.authority.enums.RoleOperation;
import account.model.authority.UserRoleOperationDto;
import account.model.password.BreachedPasswordDto;
import account.model.password.BreachedPasswordService;
import account.model.user.UserDetailsDto;
import account.model.user.UserDetailsServiceImpl;
import account.model.util.UserDeletedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @PostMapping("/breached-password")
    @ResponseStatus(HttpStatus.OK)
    public int createAll(@RequestBody List<String> stringList) {
        return passwordService.createAllDistinct(stringList);
    }

    @GetMapping("/breached-password")
    @ResponseStatus(HttpStatus.OK)
    public List<BreachedPasswordDto> findAll() {
        return passwordService.findAll();
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDetailsDto> getUsersInfo() {
        return userDetailsService.findAll();
    }

    @DeleteMapping("/user/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserDeletedResponse deleteUser(@PathVariable String email) {
        userDetailsService.deleteByEmail(email);
        return new UserDeletedResponse(email, "Deleted successfully!");
    }

    @DeleteMapping(value = {
            "/user", "/users/"
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void unsupportedOperation() {

    }

    @PutMapping("/user/role")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailsDto updateUserRole(@RequestBody UserRoleOperationDto operationDto) {
        RoleOperation operation;
        try {
            operation = RoleOperation.valueOf(operationDto.getOperation());
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException("This type of operation doesn't exist");
        }
        String email = operationDto.getUser();
        Role role;
        try {
            role = Role.valueOf(operationDto.getRole());
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException("Role not found!");
        }
        switch (operation) {
            case GRANT:
                userDetailsService.grantRoleToUserByEmail(email, role);
                break;
            case REMOVE:
                userDetailsService.removeRoleFromUserByEmail(email, role);
                break;
        }
        return userDetailsService.getByEmailAsDto(email);
    }

    public AdminController(@Autowired BreachedPasswordService passwordService,
                           @Autowired UserDetailsServiceImpl userDetailsService) {
        this.passwordService = passwordService;
        this.userDetailsService = userDetailsService;
    }

    private final BreachedPasswordService passwordService;
    private final UserDetailsServiceImpl userDetailsService;
}
