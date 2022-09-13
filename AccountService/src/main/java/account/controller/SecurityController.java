package account.controller;

import account.model.logger.Log;
import account.model.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/security")
public class SecurityController {
    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public List<Log> getLogs() {
        return logger.getLogs();
    }

    public SecurityController(@Autowired Logger logger) {
        this.logger = logger;
    }
    private final Logger logger;
}
