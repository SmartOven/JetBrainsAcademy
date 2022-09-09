package account.controller;

import account.model.breachedpassword.BreachedPasswordDto;
import account.model.breachedpassword.BreachedPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @PostMapping("/breached-password")
    @ResponseStatus(HttpStatus.OK)
    public int createAll(@RequestBody List<String> stringList) {
        return service.createAllDistinct(stringList);
    }

    @GetMapping("/breached-password")
    @ResponseStatus(HttpStatus.OK)
    public List<BreachedPasswordDto> findAll() {
        return service.findAll();
    }

    public AdminController(@Autowired BreachedPasswordService service) {
        this.service = service;
    }

    private final BreachedPasswordService service;
}
