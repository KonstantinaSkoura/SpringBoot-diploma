package project.myy803.diploma.service;

import org.springframework.stereotype.Service;
import project.myy803.diploma.model.User;

@Service
public interface UserService {
	public void saveUser(User user);
    public boolean isUserPresent(User user);
    public User getUserByUsername(String username);
}
