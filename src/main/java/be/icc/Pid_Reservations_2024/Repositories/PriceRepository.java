package be.icc.Pid_Reservations_2024.Repositories;

import be.icc.Pid_Reservations_2024.Models.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    List<Price> findByAmount(Double amount);

    @Query("SELECT p FROM Price p LEFT JOIN FETCH p.shows WHERE p.id = :id")
    Price findByIdWithShows(@Param("id") Long id);
}