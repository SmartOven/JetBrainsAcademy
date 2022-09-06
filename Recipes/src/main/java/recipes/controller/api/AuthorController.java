package recipes.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recipes.model.author.AuthorService;
import recipes.model.author.util.AuthorDto;
import recipes.model.author.util.AuthorMapper;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthorController {
    @PostMapping("/register")
    public void register(@Valid @RequestBody AuthorDto dto) {
        service.save(mapper.mappingToEntity(dto));
    }

    public AuthorController(@Autowired AuthorService service,
                            @Autowired AuthorMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    private final AuthorService service;
    private final AuthorMapper mapper;
}
