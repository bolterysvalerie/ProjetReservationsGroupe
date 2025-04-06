package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Repositories.UserRepository;
import be.icc.Pid_Reservations_2024.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogInController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/LogIn")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new User());
        return "LogIn/LogIn";
    }

    @PostMapping("/LogIn")
    public String processLogin(@ModelAttribute("loginForm") User loginForm, Model model) {

        User userFromDb = userRepository.findByLogin(loginForm.getLogin());

        // Vérification de l'utilisateur et du mot de passe
        if (userFromDb != null && passwordEncoder.matches(loginForm.getPassword(), userFromDb.getPassword())) {
            // Redirection vers le template "Home/ProfileModification.html"
            return "Home/index";
        }

        // En cas d'échec de connexion, afficher une erreur
        model.addAttribute("error", "Login ou mot de passe incorrect.");
        return "LogIn/LogIn";
    }

}
