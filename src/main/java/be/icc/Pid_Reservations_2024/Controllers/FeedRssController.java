package be.icc.Pid_Reservations_2024.Controllers;

import be.icc.Pid_Reservations_2024.Models.Representation;
import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Services.ShowService;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * This controller generates an RSS feed containing information about shows.
 * The feed includes the title, description, image, and representation schedule of each show.
 */
@RestController()
public class FeedRssController {

    // Inject ShowService to get all shows from the database
    @Autowired
    private ShowService showService;

    /**
     * Controller method to generate an RSS feed of available shows.
     *
     * @return Channel object representing the RSS feed.
     */
    @GetMapping(path = "/rss")
    public Channel rss() {
        Channel channel = new Channel();
        // Rss version
        channel.setFeedType("rss_2.0");
        // Title of Rss feed
        channel.setTitle("Feed Rss Shows");
        channel.setDescription("With RSS Feed, you will always be up to date on new shows with notifications");
        // Link to display all shows
        channel.setLink("http://localhost:8080/shows");
        //Info about who generate the feed
        channel.setGenerator("Brahms");


        // Set date and time from now to set the feed
        Date postDate = new Date();
        channel.setPubDate(postDate);

        // List of RSS items (each show is one item)
        List<Item> items = new ArrayList<>();
        for (Show show : showService.getAll()) { // Loop all shows
            Item item = new Item();
            item.setAuthor(show.getLocation().getDesignation());
            item.setLink("http://localhost:8080/show/" + show.getId());
            item.setTitle(show.getTitle());
            item.setUri("http://localhost:8080/show/" + show.getId());
            item.setComments("Le spectacle dure: " + show.getDuration() + " minutes");

            // Build the description (HTML Content)
            StringBuilder imageShowAndScheduleFromRepresentation = new StringBuilder();

            // If no poster image, use a default one
            if (show.getPosterUrl() == null) {
                show.setPosterUrl("https://cdn.shopify.com/s/files/1/0553/0442/1412/files/rideau_rouge_theatre_2048x2048.png?v=1691589090");
            }
            // Add show image to description
            imageShowAndScheduleFromRepresentation.append("<img src='")
                    .append(show.getPosterUrl())
                    .append("' alt=" + show.getTitle() + "style='width:200px;height:auto;'/><br/>");

            // Add schedule for each representation
            for (Representation representation : show.getRepresentations()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                String schedule = representation.getSchedule().format(formatter);
                String designation = representation.getLocation().getDesignation().trim();
                imageShowAndScheduleFromRepresentation.append("Le ").append(schedule)
                        .append(" à ").append(designation)
                        .append("\n");
            }

            Description description = new Description();
            description.setValue(imageShowAndScheduleFromRepresentation.toString());
            item.setDescription(description);

            // Set publication date of the item
            item.setPubDate(postDate);

            channel.setItems(Collections.singletonList(item));
            // Add the item to the list
            items.add(item);
            // Set current list of items to the channel
            channel.setItems(items);

        }
        // Return the final RSS feed
        return channel;
    }
}