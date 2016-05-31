package hello.pgexample.repository;

import hello.pgexample.services.ExtensionModel;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public abstract class BaseMongoExtensionRepository<ID extends Serializable> implements CrudRepository<ExtensionModel, ID> {
}
