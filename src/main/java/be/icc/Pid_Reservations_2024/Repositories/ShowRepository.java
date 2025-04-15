package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.Location;
import be.icc.Pid_Reservations_2024.Models.Show;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show, Integer> {

    Optional<Show> findById(long id);
    Show findBySlug(String slug);
    Show findByTitle(String title);
    List<Show> findByLocation(Location location);

    @Query("SELECT DISTINCT s FROM Show s LEFT JOIN s.tags t " +
            "WHERE (:keyword IS NULL OR :keyword = '' OR LOWER(t.tag) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Show> findByTagNameContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT s FROM Show s LEFT JOIN s.tags t " +
            "WHERE (:keyword IS NULL OR :keyword = '' OR LOWER(t.tag) NOT LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Show> findByTagsNotContaining(Pageable pageable, @Param("keyword") String keyword);


}
