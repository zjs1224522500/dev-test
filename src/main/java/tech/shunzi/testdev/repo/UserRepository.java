package tech.shunzi.testdev.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.shunzi.testdev.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);
}
