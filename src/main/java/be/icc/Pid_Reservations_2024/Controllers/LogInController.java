package be.icc.Pid_Reservations_2024.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogInController {

    @GetMapping("/login")
    public String login(
            @RequestParam(required = false) final Boolean loginRequired,
            @RequestParam(required = false) final Boolean loginError,
            @RequestParam(required = false) final Boolean logoutSuccess,
            final Model model) {

        if (loginRequired == Boolean.TRUE) {
            model.addAttribute("errorMessage", "Vous devez vous connecter pour avoir accès.");
        }

        if (loginError == Boolean.TRUE) {
            model.addAttribute("errorMessage", "Échec de la connexion !");
        }

        if (logoutSuccess == Boolean.TRUE) {
            model.addAttribute("successMessage", "Vous êtes déconnecté avec succès.");
        }

        return "login/login";
    }

}
