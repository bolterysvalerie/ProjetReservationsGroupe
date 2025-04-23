package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.RepresentationReservation;
import be.icc.Pid_Reservations_2024.Models.User;
import be.icc.Pid_Reservations_2024.Repositories.RepresentationReservationRepository;
import be.icc.Pid_Reservations_2024.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RepresentationReservationController {
    @Autowired
    private RepresentationReservationRepository representationReservationRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/RepresentationReservation")
    public String consulterRepresentations(Model model, Authentication authentication) {
        // Récupérer l'utilisateur connecté
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByLogin(currentUsername);

        // Vérifier si l'utilisateur est connecté
        if (currentUser == null) {
            model.addAttribute("error", "Utilisateur non authentifié.");
            return "error-page"; // Vous pouvez définir une page d'erreur personnalisée
        }

        // Récupérer toutes les réservations de l'utilisateur
        List<RepresentationReservation> userReservations = representationReservationRepository
                .findByReservationUserId(currentUser.getId());

        // Ajouter les données au modèle
        model.addAttribute("representations", userReservations);
        return "RepresentationReservation/ReservationRepresentation"; // Vue Thymeleaf
    }
}
