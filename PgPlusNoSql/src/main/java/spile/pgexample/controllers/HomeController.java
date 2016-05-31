package spile.pgexample.controllers;

import spile.pgexample.domain.AppUser;
import spile.pgexample.repository.UserRepository;
import spile.pgexample.services.MongoExtensionService;
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

    @Inject
    MongoExtensionService mongoExtensionService;

    @RequestMapping(value = "/user", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public AppUser create(@RequestBody AppUser user) {
        mongoExtensionService.saveExtensionOf(user);
        return userRepository.save(user);
    }

    @RequestMapping(value = "/user", method = GET, produces = APPLICATION_JSON_VALUE)
    public Iterable<AppUser> findAll() {
        for (AppUser user : userRepository.findAll()) {
            mongoExtensionService.fillExtension(user);
        }
        return userRepository.findAll();
    }

    //todo: reference to mongo extension service should be moved to the repository layer
    //so the marriage between mongo and pgsql would seem transparent to controllers

}
