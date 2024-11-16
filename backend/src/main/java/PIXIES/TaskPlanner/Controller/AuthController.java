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
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            System.out.println("Intentando registrar usuario: " + user.getUsername());

            // Codifica la contraseña antes de guardar el usuario
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Registra el usuario usando el servicio
            userService.registerUser(user);

            System.out.println("Usuario registrado exitosamente");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";  // Vuelve a la página de registro con el mensaje de error
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ocurrió un error inesperado. Por favor, inténtalo de nuevo.");
            return "register";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";  // Debe devolver "login" sin .html, Thymeleaf lo resolverá como login.html
    }



}
