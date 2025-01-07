package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<Users, Long> {

    Users findById(long id);

    Users findByLogin(String login);

    List<Users> findByLastName(String lastName);

}
