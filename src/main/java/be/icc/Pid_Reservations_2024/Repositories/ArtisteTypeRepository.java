package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.ArtisteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtisteTypeRepository extends JpaRepository<ArtisteType, Long> {

    @Query("SELECT at FROM ArtisteType at LEFT JOIN FETCH at.shows")
    List<ArtisteType> findAllWithShows();

    @Query("SELECT at FROM ArtisteType at LEFT JOIN FETCH at.shows WHERE at.id = :id")
    ArtisteType findByIdWithShows(@Param("id") Long id);
}