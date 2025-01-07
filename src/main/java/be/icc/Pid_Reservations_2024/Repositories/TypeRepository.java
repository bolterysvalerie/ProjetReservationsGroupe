package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.Types;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TypeRepository extends CrudRepository<Types, Long> {

    Optional<Types> findById(long id);

    Types findByType(String type);

    Types save(Types type);

}
