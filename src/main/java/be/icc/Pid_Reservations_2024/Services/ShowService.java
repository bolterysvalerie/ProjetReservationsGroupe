package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Repositories.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    /**
     * Gets a list of shows in pages.
     * <p>
     * This method gets shows from the database in a paginated way.
     * It returns only a part of the shows based on the page number size.
     *
     * @param pageable information about the page( how many shows per page and wich page to get)
     * @return a {@link Page} with a list of {@link Show} objects for the current page.
     */
    public Page<Show> getAllShows(Pageable pageable) {
        return showRepository.findAll(pageable);
    }


    public Show getShow(long id) {
        return showRepository.findById(id).orElse(null);
    }
}
