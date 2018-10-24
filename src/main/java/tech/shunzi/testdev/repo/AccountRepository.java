package tech.shunzi.testdev.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.shunzi.testdev.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

}
