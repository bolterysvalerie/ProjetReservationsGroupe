package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.Location;
import be.icc.Pid_Reservations_2024.Models.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show, Integer> {

    Optional<Show> findById(long id);
    Show findBySlug(String slug);
    Show findByTitle(String title);
    List<Show> findByLocation(Location location);
}
