package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Artist;
import be.icc.Pid_Reservations_2024.Services.ArtistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/artists")
    public String index(Model model) {
        List<Artist> artists = artistService.getAllArtists();

        model.addAttribute("artists", artists);
        model.addAttribute("title", "List of artists");

        return "Artist/index";
    }

    @GetMapping("/artist/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        Artist artist = artistService.getArtist(id);

        model.addAttribute("artist",artist);
        model.addAttribute("title", "Profile of an artist");

        return "Artist/show";
    }

    @GetMapping("/artist/create")
    public String create(Model model) {

        if (!model.containsAttribute("artist")) {
            model.addAttribute("artist", new Artist());
        }

        return "Artist/create";
    }

    @PostMapping("/artist/create")
    public String store(@Valid @ModelAttribute("artist") Artist artist, BindingResult bindingResult, Model model, RedirectAttributes redirAttrs) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Failure of the artist’s creation!");
            return "Artist/create";
        }

        artistService.addArtist(artist);
        redirAttrs.addFlashAttribute("successMessage", "Successfully added artist!");

        return "redirect:/artist/"+artist.getId();
    }

    @GetMapping("/artist/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id, HttpServletRequest httpServletRequest) {
        Artist artist = artistService.getArtist(id);

        model.addAttribute("artist",artist);

        // Generate the return link for cancel
        String referrer = httpServletRequest.getHeader("Referer");

        if(referrer != null && !referrer.equals("")) {
            model.addAttribute("back", referrer);
        }else {
            model.addAttribute("back", "/artist/"+artist.getId());
        }

        return "Artist/edit";
    }

    @PutMapping("/artist/{id}/edit")
    public String update(@Valid @ModelAttribute("artist") Artist artist, BindingResult bindingResult, @PathVariable("id") long id, Model model, RedirectAttributes redirAttrs) {
        if(bindingResult.hasErrors()) {
            return "Artist/edit";
        }

        Artist artistExisting = artistService.getArtist(id);

        if(artistExisting == null) {
            return "Artist/index";
        }
        artistService.updateArtist(id, artist);

        redirAttrs.addFlashAttribute("successMessage", "Artist successfully modified");

        return "redirect:/artist/"+artist.getId();
    }

    @DeleteMapping("/artist/{id}")
    public String delete(@PathVariable("id") long id, Model model, RedirectAttributes redirAttrs) {
        Artist artistExisting = artistService.getArtist(id);

        if(artistExisting != null) {
            artistService.deleteArtist(id);
            redirAttrs.addFlashAttribute("successMessage", "Artist successfully deleted!");
        } else {
            redirAttrs.addFlashAttribute("errorMessage", "Failed to delete artist !");
        }

        return "redirect:/artists";
    }

}