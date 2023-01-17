
package com.example.demo;

import com.example.demo.EmbeddedId.CustomerWithEmbedId;
import com.example.demo.EmbeddedId.CustomerWithEmbedIdRepository;
import com.example.demo.EmbeddedId.VipCustomerWithEmbedId;
import com.example.demo.IdClass.CustomerWithIdClass;
import com.example.demo.IdClass.CustomerWithIdClassRepository;
import com.example.demo.IdClass.VipCustomerWithIdClass;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CustomerWithEmbedIdRepository repositoryEmbedIdVersion,
                                  CustomerWithIdClassRepository repositoryIdClassVersion) {
        return (args) -> {
            embedIdIsOk(repositoryEmbedIdVersion);
            butIdClassFails(repositoryIdClassVersion);
        };
    }

    private void embedIdIsOk(CustomerWithEmbedIdRepository repo) {
        CustomerWithEmbedId customer = new CustomerWithEmbedId("a", "b");
        customer.setVersionId(123L);
        customer.setUnitId(456L);

        repo.save(customer); //save object of base class, ok

        customer.setFirstName("a2");
        repo.save(customer);//modify object of base class and save again, ok

        VipCustomerWithEmbedId vipCustomer = new VipCustomerWithEmbedId("a", "b", "888");
        vipCustomer.setVersionId(987L);
        vipCustomer.setUnitId(654L);

        repo.save(vipCustomer); //save object of subclass, ok

        vipCustomer.setVipNumber("999");
        repo.save(vipCustomer);//modify object of subclass and save again, ok
        // using embedded id annotation, all 4 times of saving to db ok, for both pg and mysql
    }

    private void butIdClassFails(CustomerWithIdClassRepository repository) {
        CustomerWithIdClass customer = new CustomerWithIdClass("a", "b");
        customer.setVersionId(123L);
        customer.setUnitId(456L);

        repository.save(customer);//save object of base class, ok

        customer.setFirstName("a2");
        repository.save(customer);//modify object of base class and save again, ok

        VipCustomerWithIdClass vipCustomer = new VipCustomerWithIdClass("a", "b", "888");
        vipCustomer.setVersionId(987L);
        vipCustomer.setUnitId(654L);

        repository.save(vipCustomer);//save object of subclass, ok

        vipCustomer.setVipNumber("999");
        repository.save(vipCustomer);//modify object of subclass and save again, NOT OK
        // ↑ THIS FAILS BECAUSE OF PRIMARY KEY CONFLICT. INSERT STATEMENT WAS USED INSTEAD OF UPDATE, WHY?
        // this failure only happens when:
        // 1. base class uses IdClass for composite primary key
        // 2. saving an instance of the subclass for the second time after modification
    }

}