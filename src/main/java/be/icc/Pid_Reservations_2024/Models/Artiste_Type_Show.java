package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "Artiste_Type_Show")
public class Artiste_Type_Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relation Many to One
    @ManyToOne
    @JoinColumn(name = "artist_type_id", referencedColumnName = "id", nullable = false)
    private Artiste_Type artist_type;

    @ManyToOne
    @JoinColumn(name = "show_id", referencedColumnName = "id", nullable = false)
    private Shows shows;

    // Constructor by default
    protected Artiste_Type_Show() {}

}
