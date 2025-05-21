package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "troupes")
public class Troupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 60, unique = true)
    private String name;
    @Column(name = "logo_url")
    private String logo_url;

    // Relation One To Many
    @OneToMany(mappedBy = "group", cascade = CascadeType.MERGE)
    private List<Artist> artists;

}
