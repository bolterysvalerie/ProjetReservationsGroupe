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
public class ProfileModification {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/Modification")
    public String afficherModifierProfil(Model model) {
        model.addAttribute("user", new User());
        return "Modification/ProfileModification";

    }

    // Traitement du formulaire de modification
    @PostMapping("/Modification")
    public String traiterModification(@ModelAttribute("user") User userForm, Model model) {
        // Récupérer l'utilisateur actuel depuis la base de données
        User userFromDb = userRepository.findById(userForm.getId()).orElse(null);

        if (userFromDb == null) {
            model.addAttribute("error", "Utilisateur introuvable.");
            return "Modification/ProfileModification";

        }

        // Vérification de l'ancien mot de passe
        if (!passwordEncoder.matches(userForm.getOldPassword(), userFromDb.getPassword())) {
            model.addAttribute("error", "L'ancien mot de passe est incorrect.");
            return "Modification/ProfileModification";

        }

        // Vérification des nouveaux mots de passe (si un nouveau mot de passe est fourni)
        if (userForm.getNewPassword() != null && !userForm.getNewPassword().isEmpty()) {
            if (!userForm.getNewPassword().equals(userForm.getConfirmPassword())) {
                model.addAttribute("error", "Le nouveau mot de passe et la confirmation ne correspondent pas.");
                return "Modification/ProfileModification";
            }

            // Encoder le nouveau mot de passe et mettre à jour
            userFromDb.setPassword(passwordEncoder.encode(userForm.getNewPassword()));
        }

        // Mise à jour des autres champs
        userFromDb.setLastName(userForm.getLastName());
        userFromDb.setFirstName(userForm.getFirstName());
        userFromDb.setEmail(userForm.getEmail());
        userFromDb.setLanguage(userForm.getLanguage());

        // Sauvegarder les changements dans la base de données
        userRepository.save(userFromDb);

        // Redirection après succès vers la page du profil modifié ou accueil
        return "redirect:/profil";
    }


}
