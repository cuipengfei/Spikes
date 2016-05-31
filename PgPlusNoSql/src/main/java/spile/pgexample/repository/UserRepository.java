package spile.pgexample.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import spile.pgexample.domain.AppUser;

@Repository
public interface UserRepository extends CrudRepository<AppUser,Long>{
}
