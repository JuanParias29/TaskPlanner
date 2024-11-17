package PIXIES.TaskPlanner.Services;

import PIXIES.TaskPlanner.Entity.User;
import PIXIES.TaskPlanner.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
    @Transactional
    public void registerUser(User user) {
        // Cifrar la contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Guardar el usuario en la base de datos
        userRepository.save(user);
    }


}
