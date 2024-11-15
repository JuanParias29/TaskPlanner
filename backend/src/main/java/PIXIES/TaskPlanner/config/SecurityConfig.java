package PIXIES.TaskPlanner.config;

import PIXIES.TaskPlanner.Services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpFirewall allowSemicolonHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true);  // Permite ';' en las URLs
        return firewall;
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;  // Usamos nuestro CustomUserDetailsService para la autenticación
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register").permitAll()  // Permitir acceso sin autenticación
                        .anyRequest().authenticated()  // Requiere autenticación para otras rutas
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Define la ruta del login
                        .defaultSuccessUrl("/", true)  // Redirige a la página principal después del login exitoso
                        .permitAll()  // Permite acceder a la página de login sin autenticación
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")  // Redirige a login con un mensaje cuando el logout es exitoso
                        .permitAll()
                );

        return http.build();
    }
}
