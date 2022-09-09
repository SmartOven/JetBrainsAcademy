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

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserSalaryOnPeriodController {
    @PostMapping("/acct/payments")
    @ResponseStatus(HttpStatus.OK)
    public OperationStatusResponse createUserSalaryOnPeriod(@RequestBody List<@Valid UserSalaryOnPeriodDto> userSalaryOnPeriodDtoList) {
        service.createAll(userSalaryOnPeriodDtoList);
        return new OperationStatusResponse("Added successfully!");
    }

    @PutMapping("/acct/payments")
    @ResponseStatus(HttpStatus.OK)
    public OperationStatusResponse updateExistingUserSalaryOnPeriod(@RequestBody @Valid UserSalaryOnPeriodDto dto) {
        service.update(dto);
        return new OperationStatusResponse("Updated successfully!");
    }

    @GetMapping("/empl/payment")
    @ResponseStatus(HttpStatus.OK)
    public List<UserSalaryOnPeriodInfo> findUserSalaryOnPeriodByUserDetailsAndPeriod(@RequestParam Map<String, String> params,
                                                                               @AuthenticationPrincipal UserDetails details) {
        if (!params.containsKey("period")) {
            return service.findUserSalaryOnPeriodByEmail(details.getUsername());
        }
        return List.of(service.findUserSalaryOnPeriodByEmailAndPeriod(params.get("period"), details.getUsername()));
    }

    public UserSalaryOnPeriodController(@Autowired UserSalaryOnPeriodService service) {
        this.service = service;
    }

    private final UserSalaryOnPeriodService service;
}
