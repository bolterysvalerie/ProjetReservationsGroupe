package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Models.Tag;
import be.icc.Pid_Reservations_2024.Services.ShowService;
import be.icc.Pid_Reservations_2024.Services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ShowService showService;

    @GetMapping("/createTag")
    public String createTag(@RequestParam("showId") Long showId, Model model) {
        model.addAttribute("TitleTag", "Create a new Tag");
        Show show = showService.getShow(showId);
        model.addAttribute("show", show);
        return "tag/create";
    }

    @PostMapping("/createTag")
    public String createTag(@RequestParam("newTag") String newTag,
                            @RequestParam("showId") Long showId,
                            RedirectAttributes redirAttrs)

    {
        Tag tagExisting = tagService.getByTag(newTag);

        if( tagExisting != null ) {
            redirAttrs.addFlashAttribute("error", "Tag already exists");
            return "redirect:/show/" + showId;
        }

        Tag tag = new Tag();
        tag.setTag(newTag);
        tagService.save(tag);
        Long tagId = tag.getId();

        tagService.insertShowTagRelation(tagId, showId);


        redirAttrs.addFlashAttribute("success", "Tag created");

        return "redirect:/show/" + showId;
    }

    @GetMapping("/show-with-no-tag")
    public String showWithNoTag(Model model) {
        List<Show> showWithNoTag = tagService.getShowWithoutTag();

        model.addAttribute("TitleShowWithNotTag", "Shows with no tag");
        model.addAttribute("showWithNoTag", showWithNoTag);
        return "tag/showWithoutTag";
    }


}
