package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class BackofficeController {

//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/backoffice")
//    public String dashboard(Model model) {
//        model.addAttribute("title", "Back-office");
//        return "backoffice/index";
//    }

    @Autowired
    private ShowService showService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/backoffice")
    public String dashboard(Model model) {
        // Par exemple, récupérer tous les shows en une seule page
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        List<Show> shows = showService.getAllShows(pageable).getContent();
        model.addAttribute("shows", shows);
        model.addAttribute("title", "Back-office");
        return "Backoffice/index";
    }

}