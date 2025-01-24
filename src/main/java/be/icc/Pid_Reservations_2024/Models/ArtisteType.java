package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Getter @Setter
@Table( name = "artiste_types")
public class ArtisteType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relation Many To One
    @ManyToOne
    @JoinColumn(name = "artist_id", referencedColumnName = "id", nullable = false)
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    private Type type;

    // Relation Many To Many
    @ManyToMany
    @JoinTable(
            name = "artiste_type_shows",
            joinColumns = @JoinColumn(name = "artiste_type_id"),
            inverseJoinColumns = @JoinColumn(name = "show_id")
    )
    List<Show> shows;

    // ToString
    @Override
    public String toString() {
        return "Artiste_Type{" +
                "id=" + id +
                ", artist=" + artist +
                ", type=" + type +
                '}';
    }
    
}