//package be.icc.Pid_Reservations_2024.Controllers;
//
//
//import be.icc.Pid_Reservations_2024.Models.Locality;
//import be.icc.Pid_Reservations_2024.Repositories.LocalityRepository;
//import be.icc.Pid_Reservations_2024.Services.LocalityService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//public class LocalityController {
//
//    @Autowired
//    private LocalityService localityService;
//
//    @Autowired
//    private LocalityRepository localityRepository;
//
//    @GetMapping("/localities")
//    public String index(Model model) {
//        List<Locality> localities = localityService.getAll();
//        model.addAttribute("localities", localities);
//        model.addAttribute("title", "Liste des localités");
//        return "Locality/index";
//    }
//
/// /    @GetMapping("/locality/{id}")
/// /    public String show(@PathVariable("id") long id, Model model) {
/// /        Locality locality = localityService.getLocality(id);
/// /        model.addAttribute("locality", locality);
/// /        model.addAttribute("title", "Détails de la localité");
/// /        return "Locality/show";
/// /    }
//
////    @GetMapping("/locality/{id}")
////    public String show(@PathVariable("id") long id, Model model) {
////        Locality locality = localityService.getById(id);
////        model.addAttribute("locality", locality);
////        model.addAttribute("title", "Détails de la localité");
////        return "Locality/show";
////    }
//
//    @GetMapping("/locality/{id}")
//    public String showLocality(@PathVariable Long id, Model model) {
//        Locality locality = localityRepository.findByIdWithLocations(id)
//                .orElseThrow(() -> new RuntimeException("Locality not found"));
//        model.addAttribute("locality", locality);
//        return "Locality/show";
//    }
//
//    @GetMapping("/locality/create")
//    public String create(Model model) {
//        model.addAttribute("locality", new Locality());
//        return "Locality/create";
//    }
//
//
//
//
//
//    @PostMapping("/locality/create")
//    public String store(@Valid @ModelAttribute("locality") Locality locality, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            return "Locality/create";
//        }
//        localityService.addLocality(locality);
//        return "redirect:/localities";
//    }
//
////    @GetMapping("/locality/{id}/edit")
////    public String edit(@PathVariable("id") long id, Model model, HttpServletRequest request) {
////        Locality locality = localityService.getLocality(id);
////        model.addAttribute("locality", locality);
////
////        String referrer = request.getHeader("Referer");
////        model.addAttribute("back", (referrer != null && !referrer.isEmpty()) ? referrer : "/localities");
////
////        return "Locality/edit";
////    }
//
//    @GetMapping("/locality/{id}/edit")
//    public String edit(@PathVariable("id") long id, Model model, HttpServletRequest request) {
//        Locality locality = localityService.getById(id);
//        model.addAttribute("locality", locality);
//
//        String referrer = request.getHeader("Referer");
//        model.addAttribute("back", (referrer != null && !referrer.isEmpty()) ? referrer : "/localities");
//
//        return "Locality/edit";
//    }
//
//    @PutMapping("/locality/{id}/edit")
//    public String update(@Valid @ModelAttribute("locality") Locality locality, BindingResult bindingResult, @PathVariable("id") long id, Model model) {
//        if (bindingResult.hasErrors()) {
//            return "Locality/edit";
//        }
//        localityService.updateLocality(id, locality);
//        return "redirect:/localities";
//    }
//
//    @DeleteMapping("/locality/{id}")
//    public String delete(@PathVariable("id") long id) {
//        localityService.deleteLocality(id);
//        return "redirect:/localities";
//    }
//}


package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Locality;
import be.icc.Pid_Reservations_2024.Repositories.LocalityRepository;
import be.icc.Pid_Reservations_2024.Services.LocalityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LocalityController {

    @Autowired
    private LocalityService localityService;

    @Autowired
    private LocalityRepository localityRepository;


    /**
     * Affiche la liste des localités
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/localities")
    public String index(Model model) {
        List<Locality> localities = localityService.getAll();
        model.addAttribute("localities", localities);
        model.addAttribute("title", "Liste des localités");
        return "Locality/index";
    }

    /**
     * Affiche le détail d'une localité
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/localities/{id}")
    public String showLocality(@PathVariable Long id, Model model) {
        Locality locality = localityRepository.findByIdWithLocations(id)
                .orElseThrow(() -> new RuntimeException("Locality not found"));
        model.addAttribute("locality", locality);
        return "Locality/show";
    }

    /**
     * Formulaire de création d'une localité
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/localities/create")
    public String create(Model model) {
        model.addAttribute("localityForm", new Locality());
        return "Locality/create";
    }

    /**
     * Traitement du formulaire de création
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/localities/create")
    public String store(@Valid @ModelAttribute("localityForm") Locality formLocality,
                        BindingResult bindingResult,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "Locality/create";
        }
        localityService.addLocality(formLocality);
        return "redirect:/localities";
    }

    /**
     * Formulaire d'édition d'une localité
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/localities/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model, HttpServletRequest request) {
        Locality locality = localityService.getById(id);
        model.addAttribute("localityForm", locality);

        String referrer = request.getHeader("Referer");
        model.addAttribute("back", (referrer != null && !referrer.isEmpty()) ? referrer : "/localities");

        return "Locality/edit";
    }


    /**
     * Traitement du formulaire d'édition
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/localities/{id}/edit")
    public String update(@Valid @ModelAttribute("localityForm") Locality formLocality,
                         BindingResult bindingResult,
                         @PathVariable("id") long id,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "Locality/edit";
        }
        // On sauvegarde
        localityService.updateLocality(id, formLocality);
        return "redirect:/localities";
    }

    /**
     * Suppression d'une localité
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/localities/{id}")
    public String delete(@PathVariable("id") long id) {
        localityService.deleteLocality(id);
        return "redirect:/localities";
    }
}