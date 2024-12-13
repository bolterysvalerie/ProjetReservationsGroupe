package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Localities;
import be.icc.Pid_Reservations_2024.Services.LocalitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class LocalityController {

    @Autowired
    LocalitiesService localitiesService;

    @GetMapping("/localities")
    public String index(Model model) {
        List<Localities> localities = localitiesService.getAll();
        model.addAttribute("localities", localities);
        model.addAttribute("title", "List of localities");
        return "Locality/index";
    }

    @GetMapping("/locality/{id}")
    public String show(@PathVariable long id, Model model) {
        Localities locality = localitiesService.getById(id);
        model.addAttribute("locality", locality);
        model.addAttribute("title", "Locality profile");

        return "Locality/show";
    }
}
