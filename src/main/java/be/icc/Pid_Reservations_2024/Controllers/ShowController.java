package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.*;
import be.icc.Pid_Reservations_2024.Repositories.TagRepository;
import be.icc.Pid_Reservations_2024.Services.LocationService;
import be.icc.Pid_Reservations_2024.Services.PriceService;
import be.icc.Pid_Reservations_2024.Services.ShowService;
import com.github.slugify.Slugify;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ShowController {

    @Autowired
    private ShowService showService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private TagRepository tagRepo;


    /**
     * @param page  the current page number
     * @param size  the number of shows per page
     * @param model the model used to send data to the view
     * @return the name of the view to display
     */
//    @GetMapping("/shows")
//    public String shows(@RequestParam(required = false) String date,
//                        @RequestParam(required = false) String title,
//                        @RequestParam(required = false) String duration,
//                        @RequestParam(required = false) String address,
//                        @RequestParam(required = false, defaultValue = "created_in") String sortField,
//                        @RequestParam(required = false) String sortDirection,
//                        @RequestParam(defaultValue = "0") int page,
//                        @RequestParam(defaultValue = "5") int size,
//                        Model model) {
//
//        boolean sortAsc = "on".equals(sortDirection);
//        Pageable pageable = PageRequest.of(page, size, sortAsc ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
//        Page<Show> showPage = showService.findShowsByFilters(date, title, duration,address, pageable);
//
//        model.addAttribute("shows", showPage);
//        model.addAttribute("thetitle", "Liste des spectacles");
//
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", showPage.getTotalPages());
//
//        // Pour garder les champs remplis après la recherche
//        model.addAttribute("date", date);
//        model.addAttribute("title", title);
//        model.addAttribute("duration", duration);
//        model.addAttribute("sortField", sortField);
//        model.addAttribute("sortDirection", sortDirection);
//
//        return "show/index";
//    }

    @GetMapping("/shows")
    public String shows(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String duration,
            @RequestParam(required = false) String address,
            @RequestParam(required = false, defaultValue = "created_in") String sortField,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        boolean sortAsc = "on".equalsIgnoreCase(sortDirection);
        Pageable pageable = PageRequest.of(page, size,
                sortAsc ? Sort.Direction.ASC : Sort.Direction.DESC,
                sortField);

        Page<Show> showPage;
        Integer count = null;

        if (q != null && !q.isBlank()) {
            // Appel à un nouveau service qui renvoie un Page<Show> pour la recherche par tag
            showPage = showService.searchByTag(q, pageable);
            count = Math.toIntExact(showPage.getTotalElements());
        } else {
            showPage = showService.findShowsByFilters(date, title, duration, address, pageable);
        }

        model.addAttribute("shows", showPage);
        model.addAttribute("count", count);
        model.addAttribute("q", q);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", showPage.getTotalPages());

        // conservent les autres champs de filtre
        model.addAttribute("date", date);
        model.addAttribute("title", title);
        model.addAttribute("duration", duration);
        model.addAttribute("address", address);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);

        return "show/index";
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

        return "show/show";
    }

    // --- Opérations accessibles uniquement aux ADMIN ---

    /**
     * Traite la soumission du formulaire pour créer un nouveau show.
     *
     * Accessible uniquement par les administrateurs.
     *
     * @param model             modèle pour la vue
     @return la vue "Show/create"
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/show/create")
    public String create(Model model) {
        if (!model.containsAttribute("show")) {
            model.addAttribute("show", new Show());
           // model.addAttribute("prices", priceService.getAll());
        }
        // Charger la liste des lieux disponibles
        model.addAttribute("locations", locationService.getAll());
        return "show/create";
    }


    /**
     * Affiche tous les shows qui n'ont PAS le mot‑clé donné.
     */
//    @GetMapping("/shows/without-tag/{tag}")
//    public String showsWithoutTag(@PathVariable String tag, Model model) {
//        List<Show> shows = showService.findWithoutTag(tag, pageable);
//        model.addAttribute("shows", shows);
//        model.addAttribute("count", shows.size());
//        model.addAttribute("q", tag);
//        // Vous pouvez réutiliser le même template index
//        return "show/index";
//    }
//}
    @GetMapping("/shows/without-tag/{tag}")
    public String showsWithoutTag(
            @PathVariable String tag,
            @RequestParam(required=false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "created_in") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model) {

        boolean sortAsc = "asc".equalsIgnoreCase(sortDirection);
        Pageable pageable = PageRequest.of(page, size, sortAsc ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);

        Page<Show> shows = showService.findWithoutTag(tag, pageable);
        model.addAttribute("shows", shows);
        model.addAttribute("count", shows.getTotalElements());
        model.addAttribute("q", tag);
        model.addAttribute("title", title);
        model.addAttribute("thetitle", "Spectacles sans le tag «" + tag + "»");
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", shows.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);

        return "show/index";
    }


        @PreAuthorize("hasRole('ADMIN')")
        @PostMapping("/show/{id}/tags")
        public String addTagToShow(@PathVariable Long id,
                                   @RequestParam String tagName) {
            Show show = showService.getShow(id);
            if (show == null) {
                return "redirect:/shows";
            }
            // Cherche ou crée le Tag
            Tag tag = tagRepo.findByTag(tagName)
                    .orElseGet(() -> tagRepo.save(new Tag(tagName)));
            // Met à jour la relation
            show.addTag(tag);
            showService.add(show);  // ou showService.update(...)
            return "redirect:/show/" + id;
        }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/show/create")
    public String store(
            @Valid @ModelAttribute("show") Show showForm,
            @RequestParam(value="locationId", required=false) Long locationId,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirAttrs
    ) {
        if (bindingResult.hasErrors()) {
            return "show/create";
        }

        // Gérer l'ID du location si besoin
        if (locationId != null) {
            Location loc = locationService.get(locationId.toString());
            if (loc == null) {
                model.addAttribute("errorMessage", "Le lieu sélectionné n'existe pas !");
                return "show/create";
            }
            showForm.setLocation(loc);
        }

        // Générer le slug si nécessaire
        if (showForm.getSlug() == null || showForm.getSlug().trim().isEmpty()) {
            try {
                Slugify slg = Slugify.builder().build();
                showForm.setSlug(slg.slugify(showForm.getTitle()));
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Erreur lors de la génération du slug.");
                return "show/create";
            }
        }

        if (showForm.getPrices() != null && !showForm.getPrices().isEmpty()) {
            List<Price> attachedPrices = new ArrayList<>();
            for (Price price : showForm.getPrices()) {
                List<Price> allPrices = priceService.getAll(); // Récupère tous les prix
                for (Price realPrice : allPrices) {
                    if (realPrice.getId().equals(price.getId())) { // Vérifie si l'ID correspond
                        attachedPrices.add(realPrice);
                        break;
                    }
                }
            }
            showForm.setPrices(attachedPrices);
        }

        showService.add(showForm);
        redirAttrs.addFlashAttribute("successMessage", "Show ajouté avec succès !");
        return "redirect:/show/" + showForm.getId();
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
    public String edit(@PathVariable("id") long id,
                       Model model,
                       HttpServletRequest request) {
        Show show = showService.getShow(id);
        if (show == null) {

            return "redirect:/";
        }

        model.addAttribute("show", show);
        model.addAttribute("locations", locationService.getAll());

        // Gérer le lien retour
        String referrer = request.getHeader("Referer");
        model.addAttribute("back", (referrer != null && !referrer.isEmpty()) ? referrer : "/show/" + id);

        return "show/edit";
    }

    /**
     * Traite la soumission du formulaire d'édition d'un show.
     *
     * Accessible uniquement par les administrateurs.
     *
     * @param bindingResult   résultat de la validation
     * @param id              identifiant du show
     * @param model           modèle pour la vue
     * @param redirAttrs      attributs de redirection
     * @return redirection vers le détail du show
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/show/{id}/edit")
    public String update(
            @Valid @ModelAttribute("show") Show showForm,
            @RequestParam("locationId") Long locationId,  // <-- l’ID du location sélectionné
            BindingResult bindingResult,
            @PathVariable("id") long id,
            Model model,
            RedirectAttributes redirAttrs
    ) {
        if (bindingResult.hasErrors()) {

            model.addAttribute("locations", locationService.getAll());
            return "show/edit";
        }

        // Récupère le show existant en base
        Show existingShow = showService.getShow(id);
        if (existingShow == null) {
            redirAttrs.addFlashAttribute("errorMessage", "Show introuvable !");
            return "redirect:/edit";
        }

        // Récupère le location choisi
        Location loc = locationService.get(locationId.toString());
        if (loc == null) {
            model.addAttribute("errorMessage", "Le lieu sélectionné n'existe pas !");
            model.addAttribute("locations", locationService.getAll());
            return "show/edit";
        }

        // Mettre à jour seulement les champs nécessaires
        existingShow.setTitle(showForm.getTitle());
        existingShow.setPosterUrl(showForm.getPosterUrl());
        existingShow.setDuration(showForm.getDuration());
        existingShow.setCreated_in(showForm.getCreated_in());
        existingShow.setBookable(showForm.getBookable());

        // Associer le Location existant
        existingShow.setLocation(loc);

        // Persister le tout
        showService.update(id, existingShow);

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
        return "redirect:/shows";
    }
}
