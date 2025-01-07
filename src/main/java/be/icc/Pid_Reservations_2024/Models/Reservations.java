package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Data @NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "Reservations")
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "booking_date")
    private LocalDateTime bookingDate;
    @Column(name = "status", length = 60)
    private String status;

    // Relation One To Many
    //@OneToMany(mappedBy = "reservations")
    //private Set<Representation_Reservation> representation_reservation;

    // Relation Many To One
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Users users;

}
