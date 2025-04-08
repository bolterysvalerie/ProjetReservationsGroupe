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

    //Ajout jusqu'au toString
    public ArtisteType addShow(Show show) {
        if(!this.shows.contains(show)) {
            this.shows.add(show);
            show.addArtistType(this);
        }

        return this;
    }

    public ArtisteType removeShow(Show show) {
        if(this.shows.contains(show)) {
            this.shows.remove(show);
            show.getArtistTypes().remove(this);
        }

        return this;
    }

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