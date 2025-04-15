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
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag", length = 30, nullable = false, unique = true)
    private String tag;

    @ManyToMany(mappedBy = "tags")
    private List<Show> shows;


    public Tag(String tag) {
        this.tag = tag;
    }
}
