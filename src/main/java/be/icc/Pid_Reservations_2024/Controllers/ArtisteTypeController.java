package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.ArtisteType;
import be.icc.Pid_Reservations_2024.Models.Artist;
import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Models.Type;
import be.icc.Pid_Reservations_2024.Repositories.ArtisteTypeRepository;
import be.icc.Pid_Reservations_2024.Services.ArtisteTypeService;
import be.icc.Pid_Reservations_2024.Services.ArtistService;
import be.icc.Pid_Reservations_2024.Services.ShowService;
import be.icc.Pid_Reservations_2024.Services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class ArtisteTypeController {

    @Autowired
    private ArtisteTypeService artisteTypeService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private ShowService showService;

    @Autowired
    private ArtisteTypeRepository artisteTypeRepository;

    // Afficher la liste de tous les liens ArtisteType
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/artiste-types")
    public String index(Model model) {
        List<ArtisteType> artisteTypes = artisteTypeService.getAll();
        model.addAttribute("artisteTypes", artisteTypes);
        return "ArtisteType/index";
    }

    // Afficher le formulaire de création d'un lien
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/artiste-types/create")
    public String showCreateForm(Model model) {
        // On transmet la liste des artistes et types pour alimenter les listes déroulantes
        model.addAttribute("artists", artistService.getAllArtists());
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("shows", showService.getAll());
        // On peut envoyer un objet vide pour le data binding si besoin (optionnel)
        model.addAttribute("artisteType", ArtisteType.empty());
        return "ArtisteType/create";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/artiste-types/create")
    public String createArtisteType(@RequestParam Long artistId,
                                    @RequestParam List<Long> typeIds,
                                    @RequestParam(required = false) List<Long> showIds,
                                    RedirectAttributes redirectAttributes) {

        // Récupérer l'artiste
        Artist artist = artistService.getById(artistId);

        // Récupérer la liste de types
        List<Type> types = typeService.getByIds(typeIds);

        // Récupérer la liste de shows
        List<Show> shows = new ArrayList<>();
        if (showIds != null && !showIds.isEmpty()) {
            shows = showService.getByIds(showIds);
        }

        // Créer un ArtisteType pour chaque type sélectionné
        List<ArtisteType> createdLinks = new ArrayList<>();
        for (Type type : types) {
            // Instancier un ArtisteType avec une liste vide
            ArtisteType link = ArtisteType.create(artist, type, new ArrayList<>());

            // Associer les shows sélectionnés
            for (Show s : shows) {
                link.addShow(s);   // Méthode qui met à jour la table artiste_type_shows
            }

            // Enregistrer l’entité
            createdLinks.add(artisteTypeService.create(link));
        }

        redirectAttributes.addFlashAttribute("successMessage", "Lien(s) créé(s) avec succès !");
        return "redirect:/artiste-types";
    }

    // Afficher le formulaire de modification d'un lien existant
    @GetMapping("/artiste-types/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        //ArtisteType artisteType = artisteTypeService.getById(id);
        ArtisteType artisteType = artisteTypeService.getByIdWithShows(id);
        if (artisteType == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lien introuvable !");
            return "redirect:/artiste-types";
        }

        model.addAttribute("artisteType", artisteType);
        // Pour modification, on a besoin de listes pour mettre à jour les associations
        model.addAttribute("artists", artistService.getAllArtists());
        model.addAttribute("types", typeService.getAllTypes());
        model.addAttribute("shows", showService.getAll());
        return "ArtisteType/edit"; // Fichier : templates/ArtisteType/edit.html
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/artiste-types/{id}/edit")
    public String updateArtisteType(@PathVariable Long id,
                                    @RequestParam Long artistId,
                                    @RequestParam Long typeId,
                                    @RequestParam(required = false) List<Long> showIds,
                                    RedirectAttributes redirectAttributes) {
        // Récupérer l'artiste et le type par leurs IDs
        Artist artist = artistService.getById(artistId);
        Type type = typeService.getType(typeId);
        if (artist == null || type == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Artiste ou Type introuvable !");
            return "redirect:/artiste-types";
        }

        // Récupérer l'ArtisteType existant avec ses shows initialisés
        ArtisteType artisteType = artisteTypeService.getByIdWithShows(id);
        if (artisteType == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lien introuvable !");
            return "redirect:/artiste-types";
        }

        // Mettre à jour l'artiste et le type
        artisteType.setArtist(artist);
        artisteType.setType(type);

        // Mettre à jour la liste des shows
        // Vider la collection actuelle
        artisteType.getShows().clear();
        // Si des showIds sont sélectionnés, les ajouter
        if (showIds != null && !showIds.isEmpty()) {
            List<Show> shows = showService.getByIds(showIds);
            for (Show s : shows) {
                artisteType.addShow(s);  // méthode gère la bidirectionnalité
            }
        }

        // Sauvegarder l'objet modifié
        boolean success = artisteTypeService.update(artisteType);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Lien mis à jour !");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la mise à jour !");
        }
        return "redirect:/artiste-types";
    }


    // Suppression d'un lien ArtisteType
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/artiste-types/{id}/delete")
    public String deleteArtisteType(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean success = artisteTypeService.deleteArtisteType(id);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Lien supprimé !");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression !");
        }
        return "redirect:/artiste-types";
    }
}
