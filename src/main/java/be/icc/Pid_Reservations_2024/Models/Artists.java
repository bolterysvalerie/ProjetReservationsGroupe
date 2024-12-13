package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Artists")
public class Artists {

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
    @Column(name = "birthdate", length = 150)
    private LocalDate birthdate;

    // Relation One To Many
    @OneToMany(mappedBy = "artist")
    private List<Artiste_Type> artisteTypes;

    // Constructor by default
    protected Artists() { }

    // Constructor with params
    public Artists(String birthdate, String firstname, String lastname, Long id) {
        this.birthdate = LocalDate.parse(birthdate);
        this.firstname = firstname;
        this.lastname = lastname;
        this.id = id;
    }

    public Artists(String birthdate, String firstname, String lastname) {
        this.birthdate = LocalDate.parse(birthdate);
        this.firstname = firstname;
        this.lastname = lastname;
    }

    // ToString
    @Override
    public String toString() {
        return "Artists{" +
                ", lastname= '" + lastname + '\'' +
                ", firstname= '" + firstname + '\'' +
                ", birthdate=' " + birthdate + '\'' +
                '}';
    }

}
