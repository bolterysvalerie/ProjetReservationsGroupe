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
    @Query("SELECT s FROM Show s WHERE :tag NOT MEMBER OF s.tags")
    List<Show> findShowsWithoutTag(String tag);
    @Query("SELECT s FROM Show s JOIN s.tags t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Show> findByTagKeyword(@Param("keyword") String keyword);
    @Query("SELECT s FROM Show s JOIN s.tags t WHERE t.name = :tagName")
    Page<Show> findByTags_Name(@Param("tagName") String tagName, Pageable pageable);
    @Query("SELECT s FROM Show s WHERE :tagName NOT IN (SELECT t.name FROM s.tags t)")
    List<Show> findByTags_NameNot(@Param("tagName") String tagName);


}