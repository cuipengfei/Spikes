package hello.pgexample.controllers;

import hello.pgexample.domain.AppUser;
import hello.pgexample.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class HomeController {

    @Inject
    UserRepository userRepository;

    @RequestMapping(value = "/", method = GET)
    public String sayHello() {
        return "Hello there !";
    }

    @RequestMapping(value = "/user", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public AppUser create(@RequestBody AppUser user) {
        return userRepository.save(user);
    }

    @RequestMapping(value = "/user", method = GET, produces = APPLICATION_JSON_VALUE)
    public Iterable<AppUser> findAll() {
        return userRepository.findAll();
    }

}
