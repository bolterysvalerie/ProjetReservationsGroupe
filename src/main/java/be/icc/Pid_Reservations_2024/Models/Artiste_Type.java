package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@Table( name = "Artiste_Type")
public class Artiste_Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relation One To Many
    @OneToMany(mappedBy = "artist_type")
    private List<Artiste_Type_Show> artiste_type_show;

    // Relation Many To One
    @ManyToOne
    @JoinColumn(name = "artist_id", referencedColumnName = "id", nullable = false)
    private Artists artist;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    private Types type;

    // Constructor by default
    protected Artiste_Type() {}

    // Constructor with params
    public Artiste_Type(Artists artist, Types type, Long id) {
        this.artist = artist;
        this.type = type;
        this.id = id;
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
