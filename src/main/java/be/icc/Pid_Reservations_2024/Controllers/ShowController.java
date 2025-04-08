package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Artist;
import be.icc.Pid_Reservations_2024.Models.ArtisteType;
import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Services.ShowService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ShowController {

    @Autowired
    private ShowService showService;

    /**
     * @param page  the current page number
     * @param size  the number of shows per page
     * @param model the model used to send data to the view
     * @return the name of the view to display
     */
    @GetMapping("/")
    public String shows(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model) {
        // Create pagination object
        Pageable pageable = PageRequest.of(page, size);
        // Get the shows for the current page
        Page<Show> showPage = showService.getAllShows(pageable);

        // Add All necessary data to the model to be used in the view
        model.addAttribute("shows", showPage);
        model.addAttribute("thetitle", "List of Shows");

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", showPage.getTotalPages());

        return "Show/index";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        Show show = showService.getShow(id);

        // Get artists by show and group by type
        Map<String, ArrayList<Artist>> collaborators = new HashMap<>();

        for(ArtisteType artisteType : show.getArtiste_types()) {
            String type = artisteType.getType().getType();

            if(collaborators.get(type) == null) {
                collaborators.put(type, new ArrayList<>());
            }

            collaborators.get(type).add(artisteType.getArtist());
        }

        model.addAttribute("show", show);
        model.addAttribute("collaborators", collaborators);
        model.addAttribute("TheTitle", "Details of the Show");

        return "Show/show";
    }

    // --- Opérations accessibles uniquement aux ADMIN ---

    /**
     * Affiche le formulaire de création d'un nouveau show.
     *
     * Accessible uniquement par les administrateurs.
     *
     * @param model modèle pour la vue
     * @return la vue "Show/create"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/show/create")
    public String create(Model model) {
        if (!model.containsAttribute("show")) {
            model.addAttribute("show", new Show());
        }
        return "Show/create";
    }

    /**
     * Traite la soumission du formulaire pour créer un nouveau show.
     *
     * Accessible uniquement par les administrateurs.
     *
     * @param show              le show à créer (vérifié)
     * @param bindingResult     résultat de la validation
     * @param model             modèle pour la vue
     * @param redirAttrs        attributs de redirection
     * @return redirection vers la vue de détail du show
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/show/create")
    public String store(@Valid @ModelAttribute("show") Show show,
                        BindingResult bindingResult, Model model,
                        RedirectAttributes redirAttrs) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Échec de la création du show !");
            return "Show/create";
        }

        showService.add(show);
        redirAttrs.addFlashAttribute("successMessage", "Show ajouté avec succès !");
        return "redirect:/show/" + show.getId();
    }

    /**
     * Affiche le formulaire d'édition d'un show existant.
     *
     * Accessible uniquement par les administrateurs.
     *
     * @param id              identifiant du show à modifier
     * @param model           modèle pour la vue
     * @param request         requête HTTP (pour récupérer la référence de la page précédente)
     * @return la vue "Show/edit"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/show/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model, HttpServletRequest request) {
        Show show = showService.getShow(id);
        model.addAttribute("show", show);

        // Générer le lien retour pour le bouton "Annuler"
        String referrer = request.getHeader("Referer");
        if (referrer != null && !referrer.isEmpty()) {
            model.addAttribute("back", referrer);
        } else {
            model.addAttribute("back", "/show/" + id);
        }

        return "Show/edit";
    }

    /**
     * Traite la soumission du formulaire d'édition d'un show.
     *
     * Accessible uniquement par les administrateurs.
     *
     * @param show            le show à mettre à jour
     * @param bindingResult   résultat de la validation
     * @param id              identifiant du show
     * @param model           modèle pour la vue
     * @param redirAttrs      attributs de redirection
     * @return redirection vers le détail du show
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/show/{id}/edit")
    public String update(@Valid @ModelAttribute("show") Show show,
                         BindingResult bindingResult, @PathVariable("id") long id,
                         Model model, RedirectAttributes redirAttrs) {
        if (bindingResult.hasErrors()) {
            return "Show/edit";
        }

        Show existingShow = showService.getShow(id);
        if (existingShow == null) {
            redirAttrs.addFlashAttribute("errorMessage", "Show introuvable !");
            return "redirect:/";
        }
        showService.update(id, show);
        redirAttrs.addFlashAttribute("successMessage", "Show mis à jour avec succès !");
        return "redirect:/show/" + id;
    }

    /**
     * Supprime un show existant.
     *
     * Accessible uniquement par les administrateurs.
     *
     * @param id             identifiant du show à supprimer
     * @param redirAttrs     attributs de redirection
     * @return redirection vers la page d'accueil des shows
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/show/{id}")
    public String delete(@PathVariable("id") long id, RedirectAttributes redirAttrs) {
        Show existingShow = showService.getShow(id);
        if (existingShow != null) {
            showService.delete(id);
            redirAttrs.addFlashAttribute("successMessage", "Show supprimé avec succès !");
        } else {
            redirAttrs.addFlashAttribute("errorMessage", "Erreur lors de la suppression du show !");
        }
        return "redirect:/";
    }
}
