package be.icc.Pid_Reservations_2024.Models;

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
    @ManyToOne
    @JoinColumn(name = "show_id", referencedColumnName = "id", nullable = false)
    private Show show;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    private Location location;

    @ManyToMany
    @JoinTable(
            name = "reservations",
            joinColumns = @JoinColumn(name = "representation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

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