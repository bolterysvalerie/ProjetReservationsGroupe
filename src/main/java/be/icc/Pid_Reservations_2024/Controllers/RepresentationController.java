package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Location;
import be.icc.Pid_Reservations_2024.Models.Representation;
import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Services.LocationService;
import be.icc.Pid_Reservations_2024.Services.RepresentationService;
import be.icc.Pid_Reservations_2024.Services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/representations")
public class RepresentationController {

    @Autowired
    private RepresentationService representationService;

    @Autowired
    private ShowService showService;

    @Autowired
    private LocationService locationService;

    // 1) LISTE DES REPRESENTATIONS (READ - liste)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String index(Model model) {
        List<Representation> representations = representationService.getAll();
        model.addAttribute("representations", representations);
        return "Representation/index"; // --> page thymeleaf: Representation/index.html
    }

    // 2) DETAILS D’UNE REPRESENTATION (READ - détail)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Representation rep = representationService.getById(id);
        if (rep == null) {
            return "redirect:/representations";
        }
        model.addAttribute("representation", rep);
        return "Representation/show"; // --> page thymeleaf: Representation/show.html
    }

    // 3) FORMULAIRE DE CREATION (CREATE) - GET
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String create(Model model, Pageable pageable) {
        model.addAttribute("representationForm", new Representation());

        // Charger la liste des shows et des locations pour les <select>
        model.addAttribute("shows", showService.getAllShows(pageable).getContent());
        model.addAttribute("locations", locationService.getAll());

        return "Representation/create";
    }

    // 4) TRAITEMENT DU FORMULAIRE DE CREATION (CREATE) - POST
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String store(
            @Valid @ModelAttribute("representationForm") Representation formRep,
            BindingResult bindingResult,
            @RequestParam("showId") Long showId,
            @RequestParam("locationId") Long locationId,
            Model model,
            RedirectAttributes redirectAttrs
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("shows", showService.getAllShows(null).getContent());
            model.addAttribute("locations", locationService.getAll());
            return "Representation/create";
        }

        // Récupérer le Show et le Location
        Show show = showService.getShow(showId);
        Location loc = locationService.get(locationId.toString());
        // Note : s’il y a une méthode locationService.getById(Long id), vous pouvez l’utiliser

        if (show == null || loc == null) {
            model.addAttribute("errorMessage", "Show ou Location introuvable.");
            model.addAttribute("shows", showService.getAllShows(null).getContent());
            model.addAttribute("locations", locationService.getAll());
            return "Representation/create";
        }

        // Associer au formRep
        formRep.setShow(show);
        formRep.setLocation(loc);

        // Enregistrer
        representationService.addRepresentation(formRep);

        redirectAttrs.addFlashAttribute("successMessage", "Représentation créée avec succès !");
        return "redirect:/representations";
    }

    // 5) FORMULAIRE D’EDITION (UPDATE) - GET
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Representation rep = representationService.getById(id);
        if (rep == null) {
            return "redirect:/representations";
        }

        model.addAttribute("representationForm", rep);

        // Utilise un Pageable par défaut (page 0, 100 éléments par page)
        Pageable defaultPageable = PageRequest.of(0, 100);
        model.addAttribute("shows", showService.getAllShows(defaultPageable).getContent());
        model.addAttribute("locations", locationService.getAll());

        return "Representation/edit";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/edit")
    public String update(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("representationForm") Representation formRep,
            BindingResult bindingResult,
            @RequestParam("showId") Long showId,
            @RequestParam("locationId") Long locationId,
            Model model,
            RedirectAttributes redirectAttrs
    ) {
        if (bindingResult.hasErrors()) {
            // Utiliser un Pageable par défaut (ici, page 0, 100 résultats)
            Pageable defaultPageable = PageRequest.of(0, 100);
            model.addAttribute("shows", showService.getAllShows(defaultPageable).getContent());
            model.addAttribute("locations", locationService.getAll());
            return "Representation/edit";
        }

        // Vérifier l'existant
        Representation existing = representationService.getById(id);
        if (existing == null) {
            redirectAttrs.addFlashAttribute("errorMessage", "Représentation introuvable.");
            return "redirect:/representations";
        }

        // Récupérer le Show et la Location choisis
        Show show = showService.getShow(showId);
        Location loc = locationService.get(locationId.toString());
        if (show == null || loc == null) {
            model.addAttribute("errorMessage", "Show ou Location introuvable.");
            // De nouveau, utiliser un Pageable par défaut pour recharger la liste des shows
            Pageable defaultPageable = PageRequest.of(0, 100);
            model.addAttribute("shows", showService.getAllShows(defaultPageable).getContent());
            model.addAttribute("locations", locationService.getAll());
            return "Representation/edit";
        }

        // Mettre à jour la représentation
        existing.setSchedule(formRep.getSchedule());
        existing.setShow(show);
        existing.setLocation(loc);

        representationService.updateRepresentation(id, existing);

        redirectAttrs.addFlashAttribute("successMessage", "Représentation mise à jour avec succès !");
        return "redirect:/representations";
    }


    // 7) SUPPRESSION (DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttrs) {
        Representation rep = representationService.getById(id);
        if (rep == null) {
            redirectAttrs.addFlashAttribute("errorMessage", "Représentation introuvable.");
            return "redirect:/representations";
        }

        representationService.deleteRepresentation(id);
        redirectAttrs.addFlashAttribute("successMessage", "Représentation supprimée avec succès !");
        return "redirect:/representations";
    }
}