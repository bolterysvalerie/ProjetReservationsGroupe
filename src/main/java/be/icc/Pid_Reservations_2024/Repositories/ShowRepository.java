package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show, Integer> {

    Optional<Show> findById(long id);
}
