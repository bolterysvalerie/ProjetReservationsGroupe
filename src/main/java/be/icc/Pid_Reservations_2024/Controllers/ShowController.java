package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Artist;
import be.icc.Pid_Reservations_2024.Models.ArtisteType;
import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping("/shows")
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
}
