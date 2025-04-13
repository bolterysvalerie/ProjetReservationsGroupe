package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.Locality;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LocalityRepository extends CrudRepository<Locality, Long> {

    Locality findByPostalCode(String postalCode);

    Locality findByLocality(String locality);

    // <-- Ajoute la méthode ci-dessous pour faire le fetch join
    @Query("SELECT l FROM Locality l LEFT JOIN FETCH l.locations WHERE l.id = :id")
    Optional<Locality> findByIdWithLocations(@Param("id") Long id);

}
