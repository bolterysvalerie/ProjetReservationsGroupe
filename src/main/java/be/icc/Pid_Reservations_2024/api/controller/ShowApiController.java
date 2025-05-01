package be.icc.Pid_Reservations_2024.api.controller;

import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Repositories.ShowRepository;
import be.icc.Pid_Reservations_2024.Services.ShowService;
import be.icc.Pid_Reservations_2024.api.assembler.ShowModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * This is the API controller for managing shows.
 * It handles HTTP requests related to shows, such as retrieving, creating, updating, and deleting shows.
 */
@RestController
@RequestMapping("/api")
public class ShowApiController {

    private final ShowService showService;
    private final ShowModelAssembler showAssembler;

    public ShowApiController(ShowService showService, ShowModelAssembler showAssembler) {
        this.showService = showService;
        this.showAssembler = showAssembler;
    }

    /**
     * Handles the GET request to retrieve all shows.
     *
     * @return A collection model of all shows with HATEOAS links.
     */
    @GetMapping("/shows")
    public CollectionModel<EntityModel<Show>> allShows() {
        // Fetch all shows from the database and convert each show to a HATEOAS model
        List<EntityModel<Show>> shows = showService.getAll()
                .stream()
                .map(showAssembler::toModel)
                .toList();

        // Return a CollectionModel with the list of shows and a link to the "/shows" endpoint
        return CollectionModel.of(shows,
                linkTo(methodOn(ShowApiController.class).allShows()) // Link to the allShows() method
                        .withRel("All shows"));  // Add a link to navigate to all shows)
    }

    /**
     * Handles the GET request to retrieve a specific show by ID.
     *
     * @param id The ID of the show to retrieve.
     * @return The show with the corresponding ID as an EntityModel with HATEOAS links.
     */
    @GetMapping("/show/{id}")
    public EntityModel<Show> aShow(@PathVariable long id) {
        // Retrieve the show by ID and return it as a HATEOAS model
        Show show = showService.getShow(id);
        return showAssembler.toModel(show);
    }

    /**
     * Handles the POST request to create a new show.
     *
     * @param newShow The show details to create.
     * @return A ResponseEntity with the URI of the created show and the show object.
     */
    @PostMapping("/admin/show")
    public ResponseEntity<?> newShow(@RequestBody Show newShow) {
        try {
            // Save the new show to the database
            Optional<Show> existing = showService.findBySlug(newShow.getSlug());

            if (existing.isPresent()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Le slug existe déjà.");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            Show saveNewShow = showService.add(newShow);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Show créé avec succès !");
            response.put("show", saveNewShow);


            // Return a response indicating successful creation with a link to the new show
            return ResponseEntity.created(linkTo(methodOn(ShowApiController.class)
                            .aShow(saveNewShow.getId())) // Link to the created show
                            .toUri())  // Convert the link into a URI format
                    .body(saveNewShow); // Return the created show object in the response body

        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Échec de la création du show.");
            errorResponse.put("details", e.getMessage());

            return ResponseEntity.badRequest().body(errorResponse);

        }
    }

    /**
     * Handles the PUT request to update an existing show.
     *
     * @param updatedShow The updated show details.
     * @param id          The ID of the show to update.
     * @return A ResponseEntity with the URI of the updated show and the updated show object.
     */
    @PutMapping("/admin/show/{id}")
    public ResponseEntity<?> updateShow(@RequestBody Show updatedShow, @PathVariable long id) {
        try {
            Show existingShow = showService.getShow(id);

            existingShow.setSlug(updatedShow.getSlug());
            existingShow.setTitle(updatedShow.getTitle());
            existingShow.setPosterUrl(updatedShow.getPosterUrl());
            existingShow.setDuration(updatedShow.getDuration());
            existingShow.setCreated_in(updatedShow.getCreated_in());
            existingShow.setBookable(updatedShow.getBookable());

            Map<String, String> response = new HashMap<>();
            response.put("message", "Show enregistré avec succès.");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Échec de l'enregistrement du show.");
            errorResponse.put("details", e.getMessage());

            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * Handles the DELETE request to delete a show by its ID.
     *
     * @param id The ID of the show to delete.
     * @return A ResponseEntity indicating the successful deletion.
     */
    @DeleteMapping("/admin/show/{id}")
    public ResponseEntity<?> deleteShow(@PathVariable long id) {
        Show existingShow = showService.getShow(id);

        if (existingShow != null) {
            showService.delete(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Le show avec l'ID " + id + " a bien été supprimé.");
            return ResponseEntity.ok(response);

        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Le show avec l'ID " + id + " n'existe pas, donc n'a pas été supprimé.");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

}
