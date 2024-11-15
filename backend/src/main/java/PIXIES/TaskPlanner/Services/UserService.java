package PIXIES.TaskPlanner.Services;

import PIXIES.TaskPlanner.Entity.User;
import PIXIES.TaskPlanner.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public void registerUser(User user) {
    }
}
