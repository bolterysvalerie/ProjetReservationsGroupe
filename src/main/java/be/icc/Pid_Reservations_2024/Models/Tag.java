package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag", unique = true, length = 30, nullable = false)
    private String tag;

//    @ManyToMany(
//            cascade = { CascadeType.PERSIST, CascadeType.MERGE },
//            fetch = FetchType.EAGER
//    )
//    @JoinTable(
//            name = "show_tag",
//            joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id",
//                    foreignKey = @ForeignKey(name = "fk_show_tag_tag",
//                            value = ConstraintMode.CONSTRAINT)),
//            inverseJoinColumns = @JoinColumn(name = "show_id", referencedColumnName = "id",
//                    foreignKey = @ForeignKey(name = "fk_show_tag_show",
//                            value = ConstraintMode.CONSTRAINT))
//    )

    /**
     * Côté inverse Many‑to‑Many : mappedBy vers le champ 'tags' dans Show.
     */
    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    private List<Show> shows = new ArrayList<>();


    public Tag(String tag) {
        this.tag = tag;
    }

    public Tag addShow(Show show) {
        if (!this.shows.contains(show)) {
            this.shows.add(show);
            show.getTags().add(this);
        }
        return this;
    }

    public Tag removeShow(Show show) {
        if (this.shows.remove(show)) {
            show.getTags().remove(this);
        }
        return this;
    }
}