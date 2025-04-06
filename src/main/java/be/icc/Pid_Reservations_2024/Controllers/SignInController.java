package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.User;
import be.icc.Pid_Reservations_2024.Enums.UserRoles;
import be.icc.Pid_Reservations_2024.Repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;


@Controller
public class SignInController {

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/SingIn")
    public String showInscriptionForm(Model model) {
        model.addAttribute("user", new User());
        return "SingIn/singIn";
    }


    @PostMapping("/SingIn")
    public String processSignUp(@Valid @ModelAttribute User user, BindingResult result, Model model) {
        user.setRole(UserRoles.MEMBER);
        user.setCreatedAt(LocalDateTime.now());
        if (result.hasErrors()) {
            return "SingIn/singIn";
        }

        if (userRepository.findByLogin(user.getLogin()) != null) {
            model.addAttribute("error", "Ce login est déjà pris. Veuillez en choisir un autre.");
            return "SingIn/singIn";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        model.addAttribute("success", "Inscription réussie. Veuillez vous connecter.");
        return "redirect:/logIn";
    }
}