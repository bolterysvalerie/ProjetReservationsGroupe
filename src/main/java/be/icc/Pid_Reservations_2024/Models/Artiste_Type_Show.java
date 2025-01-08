package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data @NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "Artiste_Type_Show")
public class Artiste_Type_Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relation Many to One
    @ManyToOne
    @JoinColumn(name = "artiste_type_id", referencedColumnName = "id", nullable = false)
    private Artiste_Type artiste_type;

    @ManyToOne
    @JoinColumn(name = "show_id", referencedColumnName = "id", nullable = false)
    private Shows shows;


}
