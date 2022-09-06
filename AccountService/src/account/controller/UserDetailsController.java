package account.controller;

import account.model.userdetails.UserDetailsDto;
import account.model.userdetails.UserDetailsEntity;
import account.model.userdetails.UserDetailsMapper;
import account.model.userdetails.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserDetailsController {
    @PostMapping("/auth/signup")
    @ResponseStatus(HttpStatus.OK)
    public UserDetailsDto signUpNewUser(@Valid @RequestBody UserDetailsDto dto) {
        UserDetailsEntity entity = mapper.mappingToEntity(dto);
        UserDetailsEntity createdEntity = service.create(entity);
        return mapper.mappingToDto(createdEntity);
    }

    public UserDetailsController(@Autowired UserDetailsServiceImpl service,
                                 @Autowired UserDetailsMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    private final UserDetailsServiceImpl service;
    private final UserDetailsMapper mapper;
}
