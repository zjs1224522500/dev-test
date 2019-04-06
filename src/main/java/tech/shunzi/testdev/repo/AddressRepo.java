package tech.shunzi.testdev.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.shunzi.testdev.model.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, String> {
}
