package tech.shunzi.testdev.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.shunzi.testdev.model.User;

public interface UserTestRepository extends JpaRepository<User, String> {

    User findById(int id);
}
