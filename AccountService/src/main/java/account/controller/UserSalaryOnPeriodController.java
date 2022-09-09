package account.controller;

import account.model.salary.UserSalaryOnPeriodDto;
import account.model.salary.UserSalaryOnPeriodInfo;
import account.model.salary.UserSalaryOnPeriodService;
import account.model.util.OperationStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserSalaryOnPeriodController {
    @PostMapping("/acct/payments")
    @ResponseStatus(HttpStatus.OK)
    public OperationStatusResponse createUserSalaryOnPeriod(@RequestBody List<UserSalaryOnPeriodDto> userSalaryOnPeriodDtoList) {
        service.createAll(userSalaryOnPeriodDtoList);
        return new OperationStatusResponse("Added successfully!");
    }

    @PutMapping("/acct/payments")
    @ResponseStatus(HttpStatus.OK)
    public OperationStatusResponse updateExistingUserSalaryOnPeriod(@RequestBody UserSalaryOnPeriodDto dto) {
        service.update(dto);
        return new OperationStatusResponse("Updated successfully!");
    }

    @GetMapping("/empl/payment")
    @ResponseStatus(HttpStatus.OK)
    public UserSalaryOnPeriodInfo findUserSalaryOnPeriodByUserDetailsAndPeriod(@RequestParam String period,
                                                                               @AuthenticationPrincipal UserDetails details) {
        return service.findUserSalaryOnPeriodByEmail(period, details.getUsername());
    }

    public UserSalaryOnPeriodController(@Autowired UserSalaryOnPeriodService service) {
        this.service = service;
    }

    private final UserSalaryOnPeriodService service;
}
