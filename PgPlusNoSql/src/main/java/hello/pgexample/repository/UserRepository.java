package hello.pgexample.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import hello.pgexample.domain.AppUser;

@Repository
public interface UserRepository extends CrudRepository<AppUser,Long>{
}
