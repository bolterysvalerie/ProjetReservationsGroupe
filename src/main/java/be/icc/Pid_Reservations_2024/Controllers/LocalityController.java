package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Locality;
import be.icc.Pid_Reservations_2024.Repositories.LocalityRepository;
import be.icc.Pid_Reservations_2024.Services.LocalityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LocalityController {

    @Autowired
    private LocalityService localityService;

    @Autowired
    private LocalityRepository localityRepository;


    /**
     * Affiche la liste des localités
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/localities")
    public String index(Model model) {
        List<Locality> localities = localityService.getAll();
        model.addAttribute("localities", localities);
        model.addAttribute("title", "Liste des localités");
        return "locality/index";
    }

    /**
     * Affiche le détail d'une localité
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/localities/{id}")
    public String showLocality(@PathVariable Long id, Model model) {
        Locality locality = localityRepository.findByIdWithLocations(id)
                .orElseThrow(() -> new RuntimeException("Locality not found"));
        model.addAttribute("locality", locality);
        return "locality/show";
    }

    /**
     * Formulaire de création d'une localité
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/localities/create")
    public String create(Model model) {
        model.addAttribute("localityForm", new Locality());
        return "locality/create";
    }

    /**
     * Traitement du formulaire de création
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/localities/create")
    public String store(@Valid @ModelAttribute("localityForm") Locality formLocality,
                        BindingResult bindingResult,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "locality/create";
        }
        localityService.addLocality(formLocality);
        return "redirect:/localities";
    }

    /**
     * Formulaire d'édition d'une localité
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/localities/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model, HttpServletRequest request) {
        Locality locality = localityService.getById(id);
        model.addAttribute("localityForm", locality);

        String referrer = request.getHeader("Referer");
        model.addAttribute("back", (referrer != null && !referrer.isEmpty()) ? referrer : "/localities");

        return "locality/edit";
    }


    /**
     * Traitement du formulaire d'édition
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/localities/{id}/edit")
    public String update(@Valid @ModelAttribute("localityForm") Locality formLocality,
                         BindingResult bindingResult,
                         @PathVariable("id") long id,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "locality/edit";
        }
        // On sauvegarde
        localityService.updateLocality(id, formLocality);
        return "redirect:/localities";
    }

    /**
     * Suppression d'une localité
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/localities/{id}")
    public String delete(@PathVariable("id") long id) {
        localityService.deleteLocality(id);
        return "redirect:/localities";
    }
}