package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.Location;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByDesignation(String designation);
    Optional<Location> findById(Long id);
}