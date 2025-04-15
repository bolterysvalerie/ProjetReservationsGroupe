package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Location;
import be.icc.Pid_Reservations_2024.Models.Locality;
import be.icc.Pid_Reservations_2024.Repositories.LocalityRepository;
import be.icc.Pid_Reservations_2024.Services.LocationService;
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
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocalityRepository localityRepository;

    /**
     * Liste des lieux
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/locations")
    public String index(Model model) {
        List<Location> locations = locationService.getAll();
        model.addAttribute("locations", locations);
        model.addAttribute("title", "Liste des lieux");
        return "Location/index";
    }

    /**
     * Détail d’un lieu
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/locations/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Location location = locationService.get(String.valueOf(id));
        model.addAttribute("location", location);
        return "Location/show";
    }

    /**
     * Formulaire de création
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/locations/create")
    public String create(Model model) {
        model.addAttribute("locationForm", new Location());
        model.addAttribute("localities", localityRepository.findAll());
        return "Location/create";
    }

    /**
     * Traitement du formulaire de création
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/locations/create")
    public String store(@Valid @ModelAttribute("locationForm") Location formLocation,
                        BindingResult bindingResult,
                        Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("localities", localityRepository.findAll());
            return "Location/create";
        }

        locationService.add(formLocation);
        return "redirect:/locations";
    }

    /**
     * Formulaire d’édition
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/locations/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model, HttpServletRequest request) {
        Location location = locationService.get(String.valueOf(id));
        model.addAttribute("locationForm", location);
        model.addAttribute("localities", localityRepository.findAll());

        String referrer = request.getHeader("Referer");
        model.addAttribute("back", (referrer != null && !referrer.isEmpty()) ? referrer : "/locations");

        return "Location/edit";
    }

    /**
     * Traitement du formulaire d’édition
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/locations/{id}/edit")
    public String update(@Valid @ModelAttribute("locationForm") Location formLocation,
                         BindingResult bindingResult,
                         @PathVariable("id") long id,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("localities", localityRepository.findAll());
            return "Location/edit";
        }

        locationService.update(String.valueOf(id), formLocation);
        return "redirect:/locations";
    }

    /**
     * Suppression
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/locations/{id}")
    public String delete(@PathVariable("id") long id) {
        locationService.delete(String.valueOf(id));
        return "redirect:/locations";
    }
}