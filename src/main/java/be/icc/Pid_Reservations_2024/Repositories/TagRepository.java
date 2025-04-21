package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Models.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query("SELECT s FROM Show s " +
            "JOIN s.tags t " +
            "WHERE LOWER(t.tag) LIKE LOWER(CONCAT('%', :tagName, '%'))")
    Page<Show> searchByTagName(@Param("tagName") String tagName, Pageable pageable);

    @Query("SELECT t " +
            "FROM Tag  t " +
            "JOIN t.shows s " +
            "WHERE s.id = :showId ")
    List<Tag> findByShowId(@Param("showId") Long showId);

    Optional<Tag> findByTag(String tag);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tag_shows (tag_id, show_id) " +
            "VALUES (:tagId, :showId)", nativeQuery = true)
    void insertTagShowRelation(@Param("tagId") Long tagId, @Param("showId") Long showId);

    @Query("SELECT s " +
            "FROM Show s " +
            "WHERE s.tags IS EMPTY")
    List<Show> findShowWithoutTags();
}
