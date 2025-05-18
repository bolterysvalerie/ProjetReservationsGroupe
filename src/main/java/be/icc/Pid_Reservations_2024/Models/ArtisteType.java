package be.icc.Pid_Reservations_2024.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Génère un constructeur protégé sans argument
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "artiste_types")
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
    @ManyToMany(cascade = CascadeType.MERGE)
//    @JsonBackReference("show-artist-type")
    @JsonIgnore
    @JoinTable(
            name = "artiste_type_shows",
            joinColumns = @JoinColumn(name = "artiste_type_id"),
            inverseJoinColumns = @JoinColumn(name = "show_id")
    )
    private List<Show> shows;

    public static ArtisteType create(Artist artist, Type type, List<Show> shows) {
        return new ArtisteType(artist, type, shows);
    }

    public static ArtisteType empty() {
        return new ArtisteType(null, null, new ArrayList<>());
    }


    // Constructeur supplémentaire (avec paramètres pour artist, type et shows)
    public ArtisteType(Artist artist, Type type, List<Show> shows) {
        this.artist = artist;
        this.type = type;
        this.shows = shows;
    }

    // Méthode pour ajouter un show
    public ArtisteType addShow(Show show) {
        if (!this.shows.contains(show)) {
            this.shows.add(show);
            //show.addArtistType(this);
            // Synchronise l'autre côté, si Show possède une collection d'ArtisteType
            show.getArtistTypes().add(this);
        }
        return this;
    }

    // Méthode pour retirer un show
    public ArtisteType removeShow(Show show) {
        if (this.shows.contains(show)) {
            this.shows.remove(show);
            show.getArtistTypes().remove(this);
        }
        return this;
    }

    @Override
    public String toString() {
        return "ArtistType [id=" + id + ", artist=" + artist + ", type=" + type + ", shows=" + shows + "]";
    }
}