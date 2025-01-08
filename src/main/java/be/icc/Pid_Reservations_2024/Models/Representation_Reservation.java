package be.icc.Pid_Reservations_2024.Models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data @NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "Representation_Reservation")
public class Representation_Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quantity", length = 4, columnDefinition = "TINYINT")
    private Short quantity;

    // Relation Many to One
    @ManyToOne
    @JoinColumn(name = "price_id", referencedColumnName = "id", nullable = false)
    private Prices prices;

    @ManyToOne
    @JoinColumn(name = "representation_id", referencedColumnName = "id", nullable = false)
    private Representations representations;

    @ManyToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "id", nullable = false)
    private Reservations reservations;

}