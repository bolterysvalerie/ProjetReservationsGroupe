package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Type;
import be.icc.Pid_Reservations_2024.Services.TypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TypeControlle {

    @Autowired
    TypeService typeService;

    @GetMapping("/types")
    public String index(Model model) {
        List<Type> types = typeService.getAllTypes();

        model.addAttribute("types", types);
        model.addAttribute("title", "List of types");

        return "Type/index";
    }

    @GetMapping("/type/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        Type type = typeService.getType(id);

        model.addAttribute("type", type);
        model.addAttribute("title", "Type Details");

        return "Type/show";
    }

    @GetMapping("/type/create")
    public String create(Model model) {
        Type type = new Type(null);

        model.addAttribute("addType", type);

        return "Type/create";
    }

    @PostMapping("/type/create")
    public String create(@Valid @ModelAttribute("addType") Type type, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "Type/create";
        }

        typeService.createType(type);

        return "redirect:/type/"+type.getId();
    }

    @GetMapping("/type/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id, HttpServletRequest httpServletRequest) {
        Type type = typeService.getType(id);

        model.addAttribute("onetype", type);

        // Generate the return link for cancel
        String referrer = httpServletRequest.getHeader("Referer");

        if(referrer != null && !referrer.equals("")) {
            model.addAttribute("back", referrer);
        }else {
            model.addAttribute("back", "type/"+type.getId());
        }

        return "Type/edit";
    }

    @PutMapping("/type/{id}/edit")
    public String update(@Valid @ModelAttribute("onetype") Type type, BindingResult bindingResult, @PathVariable("id") long id, Model model) {
        if(bindingResult.hasErrors()) {
            return "Type/edit";
        }

        Type typeExisting = typeService.getType(id);

        if(typeExisting == null) {
            return "Type/index";
        }

        typeService.updateType(id, type);

        return "redirect:/type/"+type.getId();
    }

    @DeleteMapping("/type/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        Type type = typeService.getType(id);

        if(type != null) {
            typeService.deleteType(id);
        }

        return "redirect:/types";
    }

}