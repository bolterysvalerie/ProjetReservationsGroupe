package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Artist;
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
    ArtistService artistService;

    @Autowired
    private TypeService typeService; // Pour récupérer la liste des types

    @Autowired
    private TroupeService troupeService;

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

        model.addAttribute("artist", artist);
        model.addAttribute("title", "Profile of an artist");

        // on veut aussi passer la liste des troupes si on affiche le form
        model.addAttribute("troupes", troupeService.getAllTroupes());

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
        model.addAttribute("troupes", troupeService.getAllTroupes());
        return "artist/create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/artist/create")
    public String store(@Valid @ModelAttribute("artist") Artist artist, BindingResult bindingResult, @RequestParam(value="troupeId", required=false) Long troupeId,  // <— récupère la sélection
                        Model model,
                        RedirectAttributes redirAttrs) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Failure of the artist’s creation!");
            // En cas d’erreur, il faut aussi recharger la liste des types
            model.addAttribute("types", typeService.getAllTypes());
            model.addAttribute("troupes", troupeService.getAllTroupes());
            return "artist/create";
        }

        // affectation de la troupe choisie (ou null si "Non affilié")
        if (troupeId != null) {
            artist.setTroupe(troupeService.getTroupe(troupeId));
        }

        artistService.addArtist(artist);
        redirAttrs.addFlashAttribute("successMessage", "Successfully added artist!");

        return "redirect:/artist/" + artist.getId();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/artist/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id, HttpServletRequest httpServletRequest) {
        Artist artist = artistService.getArtist(id);

        model.addAttribute("artist", artist);
        // Charger la liste des types disponibles pour l’édition
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("troupes", troupeService.getAllTroupes());

        // Generate the return link for cancel
        String referrer = httpServletRequest.getHeader("Referer");

        if (referrer != null && !referrer.equals("")) {
            model.addAttribute("back", referrer);
        } else {
            model.addAttribute("back", "/artist/" + artist.getId());
        }

        return "artist/edit";
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @PutMapping("/artist/{id}/edit")
//    public String update(@Valid @ModelAttribute("artist") Artist artist, BindingResult bindingResult, @PathVariable("id") long id, @RequestParam(value="troupeId", required=false) Long troupeId, Model model, RedirectAttributes redirAttrs) {
//        if (bindingResult.hasErrors()) {
//
//            return "artist/edit";
//        }
//
//        Artist artistExisting = artistService.getArtist(id);
//
//        if (artistExisting == null) {
//            return "artist/index";
//        }
//        // met à jour la troupe
//        artistExisting.setTroupe(troupeId!=null
//                ? troupeService.getTroupe(troupeId)
//                : null);
//
//        // copie les autres champs (firstname, lastname, types…)
//        artistExisting.setFirstname(artist.getFirstname());
//        artistExisting.setLastname(artist.getLastname());
//        artistExisting.setTypes(artist.getTypes());
//
////        artistService.updateArtist(id, artist);
//        artistService.updateArtist(id, artistExisting);
//
//        redirAttrs.addFlashAttribute("successMessage", "Artist successfully modified");
//
//    //    return "redirect:/artist/" + artist.getId();
//        return "redirect:/artist/" + id;
//    }

@PreAuthorize("hasRole('ADMIN')")
@PutMapping("/artist/{id}/edit")
public String update(
        @Valid @ModelAttribute("artist") Artist artist,
        BindingResult bindingResult,
        @PathVariable("id") long id,
        @RequestParam(value="troupeId", required=false) Long troupeId,
        Model model,
        RedirectAttributes redirAttrs
) {
    // 1) En cas d’erreurs de validation,
    //    on doit re-injecter types & troupes dans le modèle
    if (bindingResult.hasErrors()) {
        model.addAttribute("types",   typeService.getAllTypes());
        model.addAttribute("troupes", troupeService.getAllTroupes());
        return "artist/edit";
    }

    // 2) Charger l’artiste existant
    Artist artistExisting = artistService.getArtist(id);
    if (artistExisting == null) {
        return "redirect:/artists";
    }

    // 3) Mettre à jour les champs de l’entité existante
    artistExisting.setFirstname(artist.getFirstname());
    artistExisting.setLastname(artist.getLastname());
    artistExisting.setTypes(artist.getTypes());
    artistExisting.setTroupe(
            (troupeId != null)
                    ? troupeService.getTroupe(troupeId)
                    : null
    );

    // 4) Sauvegarde
    artistService.updateArtist(id, artistExisting);
    redirAttrs.addFlashAttribute("successMessage", "Artist successfully modified");

    // 5) Redirection sur le bon ID
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