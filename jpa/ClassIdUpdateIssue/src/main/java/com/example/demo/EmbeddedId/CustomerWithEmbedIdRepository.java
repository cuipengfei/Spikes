package com.example.demo.EmbeddedId;

import org.springframework.data.repository.CrudRepository;

public interface CustomerWithEmbedIdRepository extends CrudRepository<CustomerWithEmbedId, CustomerPK> {

}