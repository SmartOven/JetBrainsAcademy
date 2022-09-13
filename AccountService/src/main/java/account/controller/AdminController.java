package account.controller;

import account.model.authority.enums.Role;
import account.model.logger.Log;
import account.model.logger.LogEvent;
import account.model.logger.Logger;
import account.model.util.OperationStatusResponse;
import account.model.util.UserLockingOperationDto;
import account.model.util.enums.LockingOperation;
import account.model.util.enums.RoleOperation;
import account.model.util.UserRoleOperationDto;
import account.model.password.BreachedPasswordDto;
import account.model.password.BreachedPasswordService;
import account.model.user.UserDetailsDto;
import account.model.user.UserDetailsServiceImpl;
import account.model.util.UserDeletedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
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
    public UserDeletedResponse deleteUser(@PathVariable String email,
                                          @AuthenticationPrincipal UserDetails adminDetails) {
        userDetailsService.deleteByEmail(email);
        String adminEmail = adminDetails.getUsername().toLowerCase(Locale.ROOT);
        logger.log(Log.builder()
                .date(LocalDateTime.now())
                .action(LogEvent.DELETE_USER)
                .subject(adminEmail)
                .object(email)
                .path("/api/user")
                .build()
        );
        return new UserDeletedResponse(email, "Deleted successfully!");
    }

    @PutMapping("/user/role")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailsDto updateUserRole(@RequestBody UserRoleOperationDto operationDto,
                                         @AuthenticationPrincipal UserDetails adminDetails) {
        RoleOperation operation;
        try {
            operation = RoleOperation.valueOf(operationDto.getOperation());
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException("Operation doesn't exist");
        }
        String userEmail = operationDto.getUser().toLowerCase(Locale.ROOT);
        String adminEmail = adminDetails.getUsername().toLowerCase(Locale.ROOT);
        Role role;
        try {
            role = Role.valueOf(operationDto.getRole());
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException("Role not found!");
        }

        Log.LogBuilder logBuilder = Log.builder()
                .date(LocalDateTime.now())
                .subject(adminEmail)
                .path("/api/admin/user/role");
        switch (operation) {
            case GRANT:
                userDetailsService.grantRoleToUserByEmail(userEmail, role);
                logBuilder.action(LogEvent.GRANT_ROLE);
                logBuilder.object("Grant role " + role.name() + " to " + userEmail);
                break;
            case REMOVE:
                userDetailsService.removeRoleFromUserByEmail(userEmail, role);
                logBuilder.action(LogEvent.REMOVE_ROLE);
                logBuilder.object("Remove role " + role.name() + " from " + userEmail);
                break;
            default:
                throw new UnsupportedOperationException("Operation is not supported");
        }
        // Logging performed action
        logger.log(logBuilder.build());
        return userDetailsService.getByEmailAsDto(userEmail);
    }

    @PutMapping("/user/access")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OperationStatusResponse> lockOrUnlockUser(@RequestBody UserLockingOperationDto lockingOperationDto,
                                                                    @AuthenticationPrincipal UserDetails adminDetails) {
        LockingOperation operation;
        try {
            operation = LockingOperation.valueOf(lockingOperationDto.getOperation());
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException("This type of operation doesn't exist");
        }
        String email = lockingOperationDto.getUser().toLowerCase(Locale.ROOT);
        String adminEmail = adminDetails.getUsername().toLowerCase(Locale.ROOT);
        String responseStatus;

        Log.LogBuilder logBuilder = Log.builder()
                .date(LocalDateTime.now())
                .subject(adminEmail)
                .path("/api/admin/user/access");
        switch (operation) {
            case LOCK:
                userDetailsService.lockUserByEmail(email);
                responseStatus = "User " + email + " locked!";
                logBuilder.object("Lock user " + email);
                logBuilder.action(LogEvent.LOCK_USER);
                break;
            case UNLOCK:
                userDetailsService.unlockUserByEmail(email);
                responseStatus = "User " + email + " unlocked!";
                logBuilder.object("Unlock user " + email);
                logBuilder.action(LogEvent.UNLOCK_USER);
                break;
            default:
                throw new UnsupportedOperationException("Operation is not supported");
        }

        // Logging performed action
        logger.log(logBuilder.build());
        return new ResponseEntity<>(new OperationStatusResponse(responseStatus), HttpStatus.OK);
    }

    public AdminController(@Autowired BreachedPasswordService passwordService,
                           @Autowired UserDetailsServiceImpl userDetailsService,
                           @Autowired Logger logger) {
        this.passwordService = passwordService;
        this.userDetailsService = userDetailsService;
        this.logger = logger;
    }

    private final BreachedPasswordService passwordService;
    private final UserDetailsServiceImpl userDetailsService;
    private final Logger logger;
}
