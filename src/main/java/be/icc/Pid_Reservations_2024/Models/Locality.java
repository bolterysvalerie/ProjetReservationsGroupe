package be.icc.Pid_Reservations_2024.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data @NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "localities")
public class Locality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "postal_code", length = 6)
    private String postalCode;
    @Column(name = "locality", length = 60)
    private String locality;

    // Relation One To Many
    @OneToMany(mappedBy = "locality", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Location> locations;

    // Constructor with params
    public Locality(String postal_code, String locality) {
        this.postalCode = postal_code;
        this.locality = locality;
    }

    //Ajout
    public Locality addLocation(Location location) {
        if(!this.locations.contains(location)) {
            this.locations.add(location);
            location.setLocality(this);
        }

        return this;
    }

    public Locality removeLocation(Location location) {
        if(this.locations.contains(location)) {
            this.locations.remove(location);
            if(location.getLocality().equals(this)) {
                location.setLocality(null);
            }
        }

        return this;
    }

    // toString for locality and postalCode
    @Override
    public String toString() {
        return "Localities{" +
                "postal_code='" + postalCode + '\'' +
                ", locality='" + locality + '\'' +
                '}';
    }
}
