package hello.pgexample.controllers;

import hello.pgexample.domain.AppUser;
import hello.pgexample.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class HomeController {

    @Inject
    UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String sayHello() {
        return "Hello there !";
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUser create(@PathVariable String username) {
        return userRepository.save(new AppUser(username));
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<AppUser> findAll() {
        return userRepository.findAll();
    }

}
