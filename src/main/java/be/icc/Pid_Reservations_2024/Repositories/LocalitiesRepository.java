package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.Localities;
import org.springframework.data.repository.CrudRepository;

public interface LocalitiesRepository extends CrudRepository<Localities, Long> {

    Localities findByPostalCode(String postalCode);

    Localities findByLocality(String locality);

}
