package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.RepresentationReservation;
import be.icc.Pid_Reservations_2024.Models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepresentationReservationRepository extends JpaRepository<RepresentationReservation, Long> {

    List<RepresentationReservation> findByReservation(Reservation reservation);

    List<RepresentationReservation> findByReservationUserId(Long id);
}
