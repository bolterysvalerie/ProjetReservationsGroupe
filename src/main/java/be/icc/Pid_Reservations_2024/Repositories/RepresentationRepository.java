package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.Representation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepresentationRepository extends JpaRepository<Representation, Long> {

    Optional<Representation> findById(long id);
}
