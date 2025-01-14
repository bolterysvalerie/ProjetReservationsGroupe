package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.Locality;
import org.springframework.data.repository.CrudRepository;

public interface LocalitiesRepository extends CrudRepository<Locality, Long> {

    Locality findByPostalCode(String postalCode);

    Locality findByLocality(String locality);

}
