package be.icc.Pid_Reservations_2024.Models;

import com.github.slugify.Slugify;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "Shows")
public class Shows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "slug", unique = true, length = 60)
    private String slug;
    @Column(name = "title")
    private String title;
    @Column(name = "poster_url")
    private String posterUrl;
    @Column(name = "duration", length = 5, columnDefinition = "SMALLINT UNSIGNED")
    private Integer duration;
    @Column(name = "created_in")
    private Date created_in;
    @Column(name = "bookable", columnDefinition = "TINYINT")
    private Boolean bookable;

    // Relation One to Many
    @OneToMany(mappedBy = "shows")
    private List<Artiste_Type_Show> artiste_type_show;

    @OneToMany(mappedBy = "shows")
    private List<Representations> representations;

    @OneToMany(mappedBy = "shows")
    private List<Reviews> reviews;

    // Relation Many To One
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    private Locations locations;

    // Constructor by default
    protected Shows() {}

    // Constructor with params
    public Shows(String title, String posterUrl, Date created_in, Boolean bookable) {
        Slugify slg = Slugify.builder().build();

        this.slug = slg.slugify(title);
        this.title = title;
        this.posterUrl = posterUrl;
        this.created_in = created_in;
        this.bookable = bookable;
    }

    // ToString
    @Override
    public String toString() {
        return "Shows{" +
                "bookable=" + bookable +
                ", created_in=" + created_in +
                ", posterUrl='" + posterUrl + '\'' +
                ", slug='" + slug + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", locations=" + locations +
                '}';
    }
}
