package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Artist;
import be.icc.Pid_Reservations_2024.Models.Troupe;
import be.icc.Pid_Reservations_2024.Services.ArtistService;
import be.icc.Pid_Reservations_2024.Services.TroupeService;
import be.icc.Pid_Reservations_2024.Services.TypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ArtistController {

    @Autowired
    private TroupeService troupeService;

    @Autowired
    ArtistService artistService;

    @Autowired
    private TypeService typeService; // Pour récupérer la liste des types

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/artists")
    public String index(Model model) {
        List<Artist> artists = artistService.getAllArtists();

        model.addAttribute("artists", artists);
        model.addAttribute("title", "List of artists");

        return "artist/index";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/artist/{id}")
    @Transactional
    public String show(@PathVariable("id") long id, Model model) {
        Artist artist = artistService.getArtist(id);

        // Forcer le chargement de la collection lazy
        if (artist.getTypes() != null) {
            artist.getTypes().size();
        }

        List<Troupe> troupes = troupeService.getAll();

        model.addAttribute("artist", artist);
        model.addAttribute("troupes", troupes);
        model.addAttribute("title", "Profile of an artist");

        return "artist/show";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/artist/create")
    public String create(Model model) {

        if (!model.containsAttribute("artist")) {
            model.addAttribute("artist", new Artist());
        }
        // Charger la liste des types disponibles dans le modèle.
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("troupes", troupeService.getAll());
        return "artist/create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/artist/create")
    public String store(@Valid @ModelAttribute("artist") Artist artist, BindingResult bindingResult, Model model, RedirectAttributes redirAttrs) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Failure of the artist’s creation!");
            // En cas d’erreur, il faut aussi recharger la liste des types
            model.addAttribute("types", typeService.getAllTypes());
            return "artist/create";
        }

        artistService.addArtist(artist);
        redirAttrs.addFlashAttribute("successMessage", "Successfully added artist!");

        return "redirect:/artist/" + artist.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/artist/{id}/update-troupe")
    public String updateTroupe(
            @PathVariable("id") long id,
            @RequestParam(required = false) Long troupeId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Artist artist = artistService.getArtist(id);


            if (troupeId == null || troupeId == 0) {
                artist.setTroupe(null);
            } else {

                Troupe troupe = troupeService.findById(troupeId);
                artist.setTroupe(troupe);
            }

            artistService.updateArtist(id, artist);
            redirectAttributes.addFlashAttribute("successMessage", "Troupe de l'artiste mise à jour avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la mise à jour de la troupe.");
        }

        return "redirect:/artist/" + id;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/artist/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id, HttpServletRequest httpServletRequest) {
        Artist artist = artistService.getArtist(id);

        model.addAttribute("artist", artist);
        model.addAttribute("troupes", troupeService.getAll());
        // Charger la liste des types disponibles pour l’édition
        model.addAttribute("types", typeService.getAllTypes());

        // Generate the return link for cancel
        String referrer = httpServletRequest.getHeader("Referer");

        if (referrer != null && !referrer.equals("")) {
            model.addAttribute("back", referrer);
        } else {
            model.addAttribute("back", "/artist/" + artist.getId());
        }

        return "artist/edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/artist/{id}/edit")
    public String update(@Valid @ModelAttribute("artist") Artist artist,
                         BindingResult bindingResult,
                         @PathVariable("id") long id,
                         @RequestParam(name = "troupeId", required = false) Long troupeId, // Récupérer l'ID de la troupe
                         Model model,
                         RedirectAttributes redirAttrs) {
        // En cas d'erreurs de validation des champs firstname/lastname
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", typeService.getAllTypes());
            model.addAttribute("troupes", troupeService.getAll());
            return "artist/edit";
        }

        try {
            // Récupération de l'artiste existant
            Artist artistExisting = artistService.getArtist(id);

            if (artistExisting == null) {
                redirAttrs.addFlashAttribute("errorMessage", "Artiste introuvable !");
                return "redirect:/artists";
            }

            // Mise à jour des champs firstname, lastname
            artistExisting.setFirstname(artist.getFirstname());
            artistExisting.setLastname(artist.getLastname());

            // Gestion de l'association avec une troupe
            if (troupeId != null && troupeId > 0) {
                Troupe troupe = troupeService.findById(troupeId); // Charger la troupe depuis la base
                artistExisting.setTroupe(troupe); // Associer la troupe à l'artiste
            } else {
                artistExisting.setTroupe(null); // Si aucune troupe n'est sélectionnée
            }

            // Mettre à jour dans la base de données
            artistService.updateArtist(id, artistExisting);

            redirAttrs.addFlashAttribute("successMessage", "Artiste modifié avec succès !");
        } catch (Exception e) {
            redirAttrs.addFlashAttribute("errorMessage", "Erreur lors de la mise à jour de l'artiste.");
        }

        return "redirect:/artist/" + id;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/artist/{id}")
    public String delete(@PathVariable("id") long id, Model model, RedirectAttributes redirAttrs) {
        Artist artistExisting = artistService.getArtist(id);

        if (artistExisting != null) {
            artistService.deleteArtist(id);
            redirAttrs.addFlashAttribute("successMessage", "Artist successfully deleted!");
        } else {
            redirAttrs.addFlashAttribute("errorMessage", "Failed to delete artist !");
        }

        return "redirect:/artists";
    }

}