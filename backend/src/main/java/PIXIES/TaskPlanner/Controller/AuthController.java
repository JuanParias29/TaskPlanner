package PIXIES.TaskPlanner.Controller;

import PIXIES.TaskPlanner.Entity.User;
import PIXIES.TaskPlanner.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";  // Renderiza la vista de "register.html" en /templates
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        try {
            // Verifica si el controlador está siendo alcanzado
            System.out.println("Registrando usuario: " + user.getUsername());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.registerUser(user);
            return "redirect:/login";  // Redirige a login después de un registro exitoso
        } catch (Exception e) {
            // Captura cualquier error y muestra un mensaje en la consola
            e.printStackTrace();
            return "register";  // Vuelve al formulario de registro si hay un error
        }
    }

}
