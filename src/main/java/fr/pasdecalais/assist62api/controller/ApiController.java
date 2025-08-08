package fr.pasdecalais.assist62api.controller;

import com.github.javafaker.Faker;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final Faker faker = new Faker();

    @GetMapping
    public HttpStatus getApi() {
        return HttpStatus.OK;
    }

    @GetMapping("/fake")
    public String getFakeData() {
        return faker.company().industry();
    }
}
