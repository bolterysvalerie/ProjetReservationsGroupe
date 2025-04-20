package be.icc.Pid_Reservations_2024.Models;

import com.github.slugify.Slugify;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "shows")
public class Show {

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_in")
    private LocalDateTime created_in;
    @Column(name = "bookable", columnDefinition = "TINYINT")
    private Boolean bookable;

    // Relation One to Many
//    @OneToMany(targetEntity = Representation.class, mappedBy = "show", fetch = FetchType.EAGER)
    @OneToMany(targetEntity = Representation.class, mappedBy = "show",
            cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Representation> representations;

    @OneToMany(mappedBy = "show")
    private List<Review> reviews;

    // Relation Many To One
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    private Location location;

    // Relation Many To Many
    @ManyToMany(mappedBy = "shows", fetch = FetchType.EAGER)
    List<Price> prices;

    @ManyToMany(mappedBy = "shows", fetch = FetchType.EAGER)
    private List<ArtisteType> artiste_types;

    // Constructor with params
    public Show(String title, String posterUrl, LocalDateTime created_in, Boolean bookable) {
        Slugify slg = Slugify.builder().build();

        this.slug = slg.slugify(title);
        this.title = title;
        this.posterUrl = posterUrl;
        this.created_in = created_in;
        this.bookable = bookable;
    }

    //Ajout jusqu'au toString
    public Show addRepresentation(Representation representation) {
        if(!this.representations.contains(representation)) {
            this.representations.add(representation);
            representation.setShow(this);
        }

        return this;
    }

    public Show removeRepresentation(Representation representation) {
        if(this.representations.contains(representation)) {
            this.representations.remove(representation);
            if(representation.getLocation().equals(this)) {
                representation.setLocation(null);
            }
        }

        return this;
    }

    /**
     * Get the performances (artists in a type of collaboration) for the show
     */
    public List<ArtisteType> getArtistTypes() {
        return artiste_types;
    }

    public Show addArtistType(ArtisteType artistType) {
        if(!this.artiste_types.contains(artistType)) {
            this.artiste_types.add(artistType);
            artistType.addShow(this);
        }

        return this;
    }

    public Show removeArtistType(ArtisteType artistType) {
        if(this.artiste_types.contains(artistType)) {
            this.artiste_types.remove(artistType);
            artistType.getShows().remove(this);
        }

        return this;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public Show addReview(Review review) {
        if (!reviews.contains(review)) {
            reviews.add(review);
            review.setShow(this);
        }
        return this;
    }

    public Show removeReview(Review review) {
        if (reviews.contains(review)) {
            reviews.remove(review);
            review.setShow(null);
        }
        return this;
    }

    public List<Artist> getArtists() {
        List<Artist> artists = new ArrayList<>();
        for (ArtisteType artistType : this.artiste_types) {
            if (artistType.getArtist() != null) {
                artists.add(artistType.getArtist());
            }
        }
        return artists;
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
                ", locations=" + location +
                '}';
    }

}

