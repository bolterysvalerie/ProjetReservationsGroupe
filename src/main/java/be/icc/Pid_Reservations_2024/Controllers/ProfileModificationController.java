package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import be.icc.Pid_Reservations_2024.Models.User;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ProfileModificationController {
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/modification")
    public String afficherModifierProfil(@RequestParam(value = "userId", required = false) Long userId,
                                         Authentication authentication, Model model) {
        // Identifie l'utilisateur connecté
        String currentUsername = authentication.getName(); // Le nom d'utilisateur actuel (login)
        User currentUser = userRepository.findByLogin(currentUsername);

        // Si un ID est fourni dans la requête (admin), charger cet utilisateur
        User userToEdit;
        if (userId != null && currentUser.getRole().toString().equals("ADMIN")) {
            userToEdit = userRepository.findById(userId).orElse(null);
            if (userToEdit == null) {
                model.addAttribute("error", "Utilisateur introuvable.");
                return "modification/ProfileModification";
            }
        } else {
            // Sinon, c'est l'utilisateur connecté qui est modifié
            userToEdit = currentUser;
        }

        model.addAttribute("user", userToEdit);
        model.addAttribute("role", currentUser.getRole().toString());
        return "modification/ProfileModification";
    }

    @PostMapping("/modification")
    public String traiterModification(@ModelAttribute("user") User userForm,
                                      Authentication authentication, Model model) {
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByLogin(currentUsername);

        // Vérifie si l'utilisateur existe dans la base
        User userFromDb = userRepository.findById(userForm.getId()).orElse(null);
        if (userFromDb == null) {
            model.addAttribute("error", "Utilisateur introuvable.");
            return "modification/ProfileModification";
        }

        // Vérifie si l'utilisateur connecté est autorisé à faire la modification
        if (!currentUser.getRole().toString().equals("ADMIN") && !currentUser.getId().equals(userFromDb.getId())) {
            model.addAttribute("error", "Vous n'êtes pas autorisé à modifier ce profil.");
            return "redirect:/modification";
        }

//        // Validation de l'ancien mot de passe si ce n'est pas un admin
//        if (currentUser.getRole().toString().equals("MEMBER")) {
//            if (!passwordEncoder.matches(userForm.getOldPassword(), userFromDb.getPassword())) {
//                model.addAttribute("error", "L'ancien mot de passe est incorrect.");
//                return "modification/ProfileModification";
//            }
 //       }

//        // Mise à jour des champs
//        if (userForm.getNewPassword() != null && !userForm.getNewPassword().isEmpty()) {
//            if (!userForm.getNewPassword().equals(userForm.getConfirmPassword())) {
//                model.addAttribute("error", "Le nouveau mot de passe et la confirmation ne correspondent pas.");
//                return "modification/ProfileModification";
//            }
//            userFromDb.setPassword(passwordEncoder.encode(userForm.getNewPassword()));
//        }

        userFromDb.setId(userForm.getId());
        userFromDb.setLastName(userForm.getLastName());
        userFromDb.setFirstName(userForm.getFirstName());
        userFromDb.setEmail(userForm.getEmail());
        userFromDb.setLanguage(userForm.getLanguage());

        // Sauvegarde en base
        userRepository.save(userFromDb);

        return "redirect:/";
    }
}
