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
@Table(name = "Locations")
public class Locations {

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
    @OneToMany(mappedBy = "locations")
    private List<Representations> representations;

    //@OneToMany(mappedBy = "locations")
    //private List<Shows> shows;

    // Relation Many To One
    @ManyToOne
    @JoinColumn(name = "locality_id", referencedColumnName = "id", nullable = false)
    private Localities localities;

    // Constructor with params
    public Locations(String slug, String designation, String address, String website, String phone, Localities localities) {
        Slugify slg = Slugify.builder().build();

        this.slug = slg.slugify(designation);
        this.designation = designation;
        this.address = address;
        this.website = website;
        this.phone = phone;
        this.localities = localities;
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
                ", locality=" + localities +
                '}';
    }
}

