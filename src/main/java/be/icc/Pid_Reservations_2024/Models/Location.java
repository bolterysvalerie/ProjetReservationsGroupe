package be.icc.Pid_Reservations_2024.Models;

import com.github.slugify.Slugify;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data @NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "slug", unique = true, length = 60)
    private String slug;
    @Column(name = "designation", length = 60)
    private String designation;
    @Column(name = "address")
    private String address;
    @Column(name = "website")
    private String website;
    @Column(name = "phone", length = 30)
    private String phone;

    // Relation One To Many
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Representation> representations;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Show> shows;

    // Relation Many To One
    @ManyToOne
    @JoinColumn(name = "locality_id", referencedColumnName = "id", nullable = false)
    private Locality locality;

    // Constructor with params
    public Location(String slug, String designation, String address, String website, String phone, Locality locality) {
        Slugify slg = Slugify.builder().build();

        this.slug = slg.slugify(designation);
        this.designation = designation;
        this.address = address;
        this.website = website;
        this.phone = phone;
        this.locality = locality;
    }

    public Location addShow(Show show) {
        if(!this.shows.contains(show)) {
            this.shows.add(show);
            show.setLocation(this);
        }

        return this;
    }

    public Location removeShow(Show show) {
        if(this.shows.contains(show)) {
            this.shows.remove(show);
            if(show.getLocation().equals(this)) {
                show.setLocation(null);
            }
        }

        return this;
    }


    public List<Representation> getRepresentations() {
        return representations;
    }

    public Location addRepresentation(Representation representation) {
        if(!this.representations.contains(representation)) {
            this.representations.add(representation);
            representation.setLocation(this);
        }

        return this;
    }

    public Location removeRepresentation(Representation representation) {
        if(this.representations.contains(representation)) {
            this.representations.remove(representation);
            if(representation.getLocation().equals(this)) {
                representation.setLocation(null);
            }
        }

        return this;
    }

    // ToString
    @Override
    public String toString() {
        return "Locations{" +
                "id=" + id +
                ", slug='" + slug + '\'' +
                ", designation='" + designation + '\'' +
                ", address='" + address + '\'' +
                ", website='" + website + '\'' +
                ", phone='" + phone + '\'' +
                ", locality=" + locality +
                '}';
    }
}

