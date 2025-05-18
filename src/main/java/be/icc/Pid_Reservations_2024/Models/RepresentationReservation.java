package be.icc.Pid_Reservations_2024.Models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data @NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "representation_reservations")
public class RepresentationReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quantity", length = 4, columnDefinition = "TINYINT")
    private Short quantity;

    // Relation Many to One
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "price_id", referencedColumnName = "id", nullable = false)
    private Price price;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "representation_id", referencedColumnName = "id", nullable = false)
    private Representation representation;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "reservation_id", referencedColumnName = "id", nullable = false)
    private Reservation reservation;

}