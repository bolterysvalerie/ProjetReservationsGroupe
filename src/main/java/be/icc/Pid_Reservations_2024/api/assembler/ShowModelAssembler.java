package be.icc.Pid_Reservations_2024.api.assembler;

import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.api.controller.ShowApiController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Converts a `Show` object into an `EntityModel<Show>` and adds HATEOAS links to it.
 * HATEOAS (Hypermedia As The Engine Of Application State) is used to provide links
 * that can guide the client through available actions in the API.
 */
@Component
public class ShowModelAssembler implements RepresentationModelAssembler<Show, EntityModel<Show>> {

    /**
     * Converts an `Show` object into an `EntityModel<Artist>`, adding HATEOAS links.
     * HATEOAS => Hypermedia As The Engine Of Application State
     *
     * @param show The Show object to convert.
     * @return An `EntityModel<Show>` containing the Show object and links to related actions.
     */
    @Override
    public EntityModel<Show> toModel(Show show) {
        // Create and return an EntityModel containing the show and relevant HATEOAS links
        return EntityModel.of(show,
                linkTo(methodOn(ShowApiController.class).allShows()).withRel("All shows"),// Link to get a list of all shows
                linkTo(methodOn(ShowApiController.class).aShow(show.getId())).withSelfRel(),// Self link to get the specific show by its ID
                linkTo(methodOn(ShowApiController.class).newShow(show)).withRel("Create new Show"),// Link to create a new show
                linkTo(methodOn(ShowApiController.class).updateShow(show, show.getId())).withRel("Update show"),// Link to update the specific show by its ID
                linkTo(methodOn(ShowApiController.class).deleteShow(show.getId())).withRel("Delete")// Link to delete the show by its ID
        );

    }

}
