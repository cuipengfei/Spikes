package hello.pgexample.controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import hello.pgexample.domain.AppUser;
import hello.pgexample.domain.Customer;
import hello.pgexample.repository.CustomerRepository;
import hello.pgexample.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.UnknownHostException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class HomeController {
    MongoClient mongo = new MongoClient("localhost", 27017);

    @Inject
    UserRepository userRepository;

    @Inject
    CustomerRepository customerRepository;

    public HomeController() throws UnknownHostException {
    }

    @RequestMapping(value = "/", method = GET)
    public String sayHello() {
        return "Hello there !";
    }

    @RequestMapping(value = "/user", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public AppUser create(@RequestBody AppUser user) {
        DBCollection collection = mongo.getDB("test").getCollection(user.getClass().getSimpleName() + "_extensions");
        BasicDBObject document = new BasicDBObject();
        document.put("a", "b");
        collection.insert(document);
        return userRepository.save(user);
    }

    @RequestMapping(value = "/customer", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Customer newMongoCustomer(@RequestBody Customer customer) {
        return customerRepository.insert(customer);
    }

    @RequestMapping(value = "/user", method = GET, produces = APPLICATION_JSON_VALUE)
    public Iterable<AppUser> findAll() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/customer", method = GET, produces = APPLICATION_JSON_VALUE)
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

}
