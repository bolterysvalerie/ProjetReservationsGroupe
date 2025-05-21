package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Artist;
import be.icc.Pid_Reservations_2024.Models.Troupe;
import be.icc.Pid_Reservations_2024.Services.ArtistService;
import be.icc.Pid_Reservations_2024.Services.TroupeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TroupeController {

    @Autowired
    TroupeService troupeService;
    ArtistService artistService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/troupes")
    public String troupes(Model model) {
        List<Troupe> troupes = troupeService.findAll();

        model.addAttribute("troupes", troupes);

        return "troupe/formulaireTroupe";
    }

    @PostMapping("/save-troupe")
    public String addTroupe(@RequestParam("artistId") Long artistId,
                            @RequestParam(value = "troupeId", required = false) Long troupeId,
                            RedirectAttributes redirectAttributes) {

        Artist artist = artistService.getArtist(artistId);

        if (troupeId != null) {
            Troupe troupe = troupeService.findById(troupeId);
            artist.setGroup(troupe);
        } else {
            artist.setGroup(null); // Si "Non affilié"
        }

        artistService.addArtist(artist); // Assurez-vous d'avoir une méthode save

        redirectAttributes.addFlashAttribute("message", "Artiste mis à jour avec succès.");

        return "redirect:/artists"; // ou autre redirection pertinente
    }

}
