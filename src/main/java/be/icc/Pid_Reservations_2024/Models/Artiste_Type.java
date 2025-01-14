package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Getter @Setter
@Table( name = "artiste_type")
public class Artiste_Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relation Many To One
    @ManyToOne
    @JoinColumn(name = "artist_id", referencedColumnName = "id", nullable = false)
    private Artists artist;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    private Types type;

    @ManyToOne
    @JoinColumn(name = "show_id", referencedColumnName = "id", nullable = false)
    private Shows shows;

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