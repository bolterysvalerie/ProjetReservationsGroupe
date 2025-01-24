package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data @NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "types")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type", length = 60)
    @NotBlank(message = "The type must not be empty")
    @Size(max=60, message = "The type must be 60 characters")
    private String type;

    // Relation Many to Many
    @ManyToMany
    @JoinTable(
            name = "artiste_types",
            joinColumns = @JoinColumn(name = "type_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private List<Artist> artists;


    // Constructor with params
    public Type(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    // Constructor without id
    public Type(String type) {
        this.type = type;
    }

    // ToString
    @Override
    public String toString() {
        return "Types{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
