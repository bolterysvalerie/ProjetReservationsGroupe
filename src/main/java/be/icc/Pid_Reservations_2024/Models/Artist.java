package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;


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
    @ManyToMany(mappedBy ="artists")
    private List<Type> types;

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

    //Ajout jusqu'au toString
    public Artist addType(Type type) {
        if(!this.types.contains(type)) {
            this.types.add(type);
            type.addArtist(this);
        }

        return this;
    }

    public Artist removeType(Type type) {
        if(this.types.contains(type)) {
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