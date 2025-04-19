package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.Location;
import be.icc.Pid_Reservations_2024.Models.Show;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show, Integer> {

    Optional<Show> findById(long id);

    Show findBySlug(String slug);

    Show findByTitle(String title);

    List<Show> findByLocation(Location location);

    @Query("SELECT s FROM Show s " +
            "WHERE (:date IS NULL OR DATE(s.created_in) = :date) " +
            "AND (:title IS NULL OR LOWER(s.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:duration IS NULL OR s.duration = :duration) " +
            "AND (:address IS NULL OR LOWER(s.location.address) LIKE LOWER(CONCAT('%', :address, '%')))")
    Page<Show> findByFilters(@Param("date") LocalDate date,
                             @Param("title") String title,
                             @Param("duration") String duration,
                             @Param("address") String address,
                             Pageable pageable);
}