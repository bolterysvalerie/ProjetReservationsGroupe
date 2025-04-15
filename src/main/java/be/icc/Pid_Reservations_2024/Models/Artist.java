package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.util.ArrayList;
import java.util.List;


@Data @NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "lastname", length = 60)
    @NotEmpty(message = "The lastname must no be empty")
    @Size(min=2, max=60, message = "The lastname must be between 2 and 60 characters")
    private String lastname;
    @Column(name = "firstname", length = 60)
    @NotEmpty(message = "The firstname must no be empty")
    @Size(min=2, max=60, message = "The firstname must be between 2 and 60 characters")
    private String firstname;

    // Relation Many To Many
    @ManyToMany(mappedBy = "artists" , fetch = FetchType.EAGER)
    List<Type> types; // Initialiser la collection pour éviter les NullPointerException


    // Constructor with params
    public Artist(String firstname, String lastname, Long id) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.id = id;
    }

    public Artist(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    // Méthode pour ajouter un Type à l'Artist
    public Artist addType(Type type) {
        // S'assurer que la liste est initialisée
        if (this.types == null) {
            this.types = new ArrayList<>();
        }
        // Si le type n'est pas déjà présent, l'ajouter et mettre à jour l'autre côté
        if (!this.types.contains(type)) {
            this.types.add(type);
            //type.addArtist(this);
        }
        return this;
    }

    // Méthode pour retirer un Type de l'Artist
    public Artist removeType(Type type) {
        if (this.types != null && this.types.contains(type)) {
            this.types.remove(type);
            type.getArtists().remove(this);
        }
        return this;
    }

    // ToString
    @Override
    public String toString() {
        return "Artists{" +
                ", lastname= '" + lastname + '\'' +
                ", firstname= '" + firstname + '\'' +
                '}';
    }

}