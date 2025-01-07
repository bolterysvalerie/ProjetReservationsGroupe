package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.Artists;
import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository extends CrudRepository<Artists, Long> {

    Artists findById(long id);

    Artists save(Artists artists);
}
