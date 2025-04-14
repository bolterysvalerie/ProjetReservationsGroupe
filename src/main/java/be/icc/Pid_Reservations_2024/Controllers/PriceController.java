package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Price;
import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Services.PriceService;
import be.icc.Pid_Reservations_2024.Services.ShowService;
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
@RequestMapping("/prices")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @Autowired
    private ShowService showService;


    // 1) LISTE PRICES
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String index(Model model) {
        List<Price> prices = priceService.getAll();
        model.addAttribute("prices", prices);
        return "Price/index";
    }

    // 2) DETAILS PRICE
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Price price = priceService.getOne(id);
        if (price == null) {
            return "redirect:/prices";
        }
        model.addAttribute("price", price);
        return "Price/show";
    }

    // 3) FORMULAIRE DE CREATION - GET
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String create(Model model) {
        // Price vide pour le binder
        model.addAttribute("price", new Price());
        // Charger tous les shows pour affichage (checkbox ou select multiple)
        model.addAttribute("shows", showService.getAll());
        return "Price/create";
    }

    // 4) TRAITEMENT CREATION - POST
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("price") Price priceForm,
                        BindingResult bindingResult,
                        @RequestParam(value = "showIds", required = false) List<Long> showIds,
                        RedirectAttributes redirAttrs,
                        Model model) {
        if (bindingResult.hasErrors()) {
            // En cas d’erreur, on ré-affiche la page avec la liste de shows
            model.addAttribute("shows", showService.getAll());
            return "Price/create";
        }

        // Associer les shows sélectionnés
        if (showIds != null && !showIds.isEmpty()) {
            List<Show> attachedShows = showService.getByIds(showIds);
            priceForm.setShows(attachedShows);
        }

        // Enregistrer
        priceService.add(priceForm);

        redirAttrs.addFlashAttribute("successMessage", "Nouveau prix créé avec association de shows !");
        return "redirect:/prices";
    }

    // 5) FORMULAIRE EDIT - GET
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Price price = priceService.getOne(id);
        if (price == null) {
            return "redirect:/prices";
        }
        model.addAttribute("price", price);
        // Charger tous les shows pour l'édition
        model.addAttribute("shows", showService.getAll());
        return "Price/edit";
    }

    // 6) TRAITEMENT EDIT - PUT
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("price") Price formPrice,
                         BindingResult bindingResult,
                         @RequestParam(value = "showIds", required = false) List<Long> showIds,
                         RedirectAttributes redirAttrs,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("shows", showService.getAll());
            return "Price/edit";
        }

        // Charger l’existant
        Price existing = priceService.getOne(id);
        if (existing == null) {
            return "redirect:/prices";
        }

        // Associer éventuellement les shows
        if (showIds != null && !showIds.isEmpty()) {
            List<Show> attachedShows = showService.getByIds(showIds);
            existing.setShows(attachedShows);
        } else {
            // Si rien n’est sélectionné, on vide la liste
            existing.getShows().clear();
        }

        // Mettre à jour d’autres champs
        existing.setType(formPrice.getType());
        existing.setAmount(formPrice.getAmount());
        existing.setStart_date(formPrice.getStart_date());
        existing.setEnd_date(formPrice.getEnd_date());
        // etc.

        priceService.update(id, existing);
        redirAttrs.addFlashAttribute("successMessage", "Prix mis à jour et shows associés !");
        return "redirect:/prices";
    }

    // 7) SUPPRESSION
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirAttrs) {
        Price existing = priceService.getOne(id);
        if (existing != null) {
            priceService.delete(id);
            redirAttrs.addFlashAttribute("successMessage", "Prix supprimé !");
        }
        return "redirect:/prices";
    }
}