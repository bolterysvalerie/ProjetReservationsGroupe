package be.icc.Pid_Reservations_2024.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data @NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "representations")
public class Representation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "schedule")
    private LocalDateTime schedule;

    // Relation Many to One
    @OneToMany(mappedBy = "representation", fetch = FetchType.EAGER)
    @JsonBackReference("")
    private List<RepresentationReservation> representation_reservations;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "show_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference("show-representation")
    private Show show;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference("location-representation")
    private Location location;

    // Constructor with params
    public Representation(Long id, LocalDateTime schedule, Show show) {
        this.id = id;
        this.schedule = schedule;
        this.show = show;
    }


    // ToString
    @Override
    public String toString() {
        return "Representations{" +
                "id=" + id +
                ", schedule=" + schedule +
                ", show=" + show +
                '}';
    }
}