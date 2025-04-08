package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Enums.UserRoles;
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

        if (userFromDb != null && passwordEncoder.matches(loginForm.getPassword(), userFromDb.getPassword())) {
            return "Home/index";
        }

        model.addAttribute("error", "Login ou mot de passe incorrect.");
        return "LogIn/LogIn";
    }

//    // Traitement de la connexion
//    @PostMapping("/LogIn")
//    public String processLogin(@ModelAttribute("loginForm") User loginForm, Model model) {
//        // Recherche de l'utilisateur en base de données
//        User userFromDb = userRepository.findByLogin(loginForm.getLogin());
//
//        // Vérification utilisateur et mot de passe
//        if (userFromDb != null && passwordEncoder.matches(loginForm.getPassword(), userFromDb.getPassword())) {
//
//            // Vérification si l'utilisateur est un administrateur
//            if (UserRoles.ADMIN.equals(userFromDb.getRole())) {
//                model.addAttribute("message", "Bienvenue, Administrateur !");
//                System.out.println(userFromDb.getRole());
//                return "Artist/index"; // Vue pour l'administrateur
//            }
//
//            // Si ce n'est pas un administrateur
//            model.addAttribute("message", "Bienvenue, " + userFromDb.getFirstName() + " !");
//            System.out.println(userFromDb.getRole());
//            return "Modification/ProfileModification"; // Vue pour les utilisateurs
//        }
//
//        // Gestion des erreurs d'identification
//        model.addAttribute("error", "Login ou mot de passe incorrect.");
//        return "LogIn/LogIn";
//    }


}
