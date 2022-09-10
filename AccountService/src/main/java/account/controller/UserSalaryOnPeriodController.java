package account.controller;

import account.exception.DataManagementException;
import account.model.salary.UserSalaryOnPeriodDto;
import account.model.salary.UserSalaryOnPeriodInfo;
import account.model.salary.UserSalaryOnPeriodService;
import account.model.util.OperationStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserSalaryOnPeriodController {
    @PostMapping("/acct/payments")
    @ResponseStatus(HttpStatus.OK)
    public OperationStatusResponse createUserSalaryOnPeriod(@RequestBody @Valid List<UserSalaryOnPeriodDto> userSalaryOnPeriodDtoList,
                                                            BindingResult errors) {
        if (errors.hasErrors()) { // validate errors handling
            throw new DataManagementException(errors);
        }
        service.createAll(userSalaryOnPeriodDtoList);
        return new OperationStatusResponse("Added successfully!");
    }

    @PutMapping("/acct/payments")
    @ResponseStatus(HttpStatus.OK)
    public OperationStatusResponse updateExistingUserSalaryOnPeriod(@RequestBody @Valid UserSalaryOnPeriodDto dto,
                                                                    BindingResult errors) {
        if (errors.hasErrors()) { // validate errors handling
            throw new DataManagementException(errors);
        }
        service.update(dto);
        return new OperationStatusResponse("Updated successfully!");
    }

    @GetMapping("/empl/payment")
    public ResponseEntity<?> findUserSalaryOnPeriodByUserDetailsAndPeriod(@RequestParam Map<String, String> params,
                                                                       @AuthenticationPrincipal UserDetails details) {
        if (!params.containsKey("period")) {
            return new ResponseEntity<>(
                    service.findUserSalaryOnPeriodByEmail(details.getUsername()),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                service.findUserSalaryOnPeriodByEmailAndPeriod(params.get("period"), details.getUsername()),
                HttpStatus.OK
        );
    }

    public UserSalaryOnPeriodController(@Autowired UserSalaryOnPeriodService service) {
        this.service = service;
    }

    private final UserSalaryOnPeriodService service;
}
