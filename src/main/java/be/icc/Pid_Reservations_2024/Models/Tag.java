package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data @NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tag", unique = true, nullable = false, length = 30)
    private String tag;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tag_shows",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "show_id")
    )
    List<Show> shows;

    @Override
    public String toString() {
        return "Tag{" +
                "tag='" + tag + '\'' +
                '}';
    }
}
