package project.myy803.diploma.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.myy803.diploma.model.User;

public interface UserDAO extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}