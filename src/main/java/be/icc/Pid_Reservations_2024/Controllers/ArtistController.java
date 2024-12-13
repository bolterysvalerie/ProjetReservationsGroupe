package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Artists;
import be.icc.Pid_Reservations_2024.Services.ArtistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ArtistController {

    @Autowired
    ArtistService artistService;

    @GetMapping("/artists")
    public String index(Model model) {
        List<Artists> artists = artistService.getAllArtists();

        model.addAttribute("artists", artists);
        model.addAttribute("title", "List of artists");

        return "Artist/index";
    }

    @GetMapping("/artist/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        Artists artist = artistService.getArtist(id);

        model.addAttribute("artist",artist);
        model.addAttribute("title", "Profile of an artist");

        return "Artist/show";
    }

    @GetMapping("/artist/create")
    public String create(Model model) {
        Artists artist = new Artists("1900-01-01",null,null);

        model.addAttribute("artist",artist);

        return "Artist/create";
    }

    @PostMapping("/artist/create")
    public String create(@Valid @ModelAttribute("artist") Artists artist, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "Artist/create";
        }

        artistService.addArtist(artist);

        return "redirect:/artist/"+artist.getId();
    }

    @GetMapping("/artist/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id, HttpServletRequest httpServletRequest) {
        Artists artist = artistService.getArtist(id);

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
    public String update(@Valid @ModelAttribute("artist") Artists artist, BindingResult bindingResult, @PathVariable("id") long id,Model model) {
        if(bindingResult.hasErrors()) {
            return "Artist/edit";
        }

        Artists artistExisting = artistService.getArtist(id);

        if(artistExisting == null) {
            return "Artist/index";
        }
        artistService.updateArtist(id, artist);

        return "redirect:/artist/"+artist.getId();
    }

    @DeleteMapping("/artist/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        Artists artistExisting = artistService.getArtist(id);

        if(artistExisting != null) {
            artistService.deleteArtist(id);
        }

        return "redirect:/artists";
    }

}
