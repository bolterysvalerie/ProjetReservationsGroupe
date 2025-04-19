package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import be.icc.Pid_Reservations_2024.Models.User;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ProfileModificationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/modification")
    public String afficherModifierProfil(Model model) {
        model.addAttribute("user", new User());
        return "modification/profileModification";

    }

    @PostMapping("/modification")
    public String traiterModification(@ModelAttribute("user") User userForm, Model model) {
        User userFromDb = userRepository.findById(userForm.getId()).orElse(null);

        if (userFromDb == null) {
            model.addAttribute("error", "Utilisateur introuvable.");
            return "modification/profileModification";

        }

        if (!passwordEncoder.matches(userForm.getOldPassword(), userFromDb.getPassword())) {
            model.addAttribute("error", "L'ancien mot de passe est incorrect.");
            return "modification/profileModification";

        }


        if (userForm.getNewPassword() != null && !userForm.getNewPassword().isEmpty()) {
            if (!userForm.getNewPassword().equals(userForm.getConfirmPassword())) {
                model.addAttribute("error", "Le nouveau mot de passe et la confirmation ne correspondent pas.");
                return "modification/profileModification";
            }

            userFromDb.setPassword(passwordEncoder.encode(userForm.getNewPassword()));
        }

        userFromDb.setLastName(userForm.getLastName());
        userFromDb.setFirstName(userForm.getFirstName());
        userFromDb.setEmail(userForm.getEmail());
        userFromDb.setLanguage(userForm.getLanguage());

        userRepository.save(userFromDb);

        return "modification/profileModification";
    }


}
