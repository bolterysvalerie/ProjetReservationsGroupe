package be.icc.Pid_Reservations_2024.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data @NoArgsConstructor
@Entity
@Getter @Setter
@Table(name = "prices")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type", length = 30)
    private String type;
    @Column(name = "amount", nullable = false, length = 10, precision = 2)
    private Double amount;
    @Column(name = "start_date")
    private LocalDate start_date;
    @Column(name = "end_date")
    private LocalDate end_date;

    // Relation One To Many
    @OneToMany(mappedBy = "price", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<RepresentationReservation> representation_reservations;

    // Relation Many to Many
    //@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "price_shows",
            joinColumns = @JoinColumn(name = "price_id"),
            inverseJoinColumns = @JoinColumn(name = "show_id")
    )
    List<Show> shows;

    // Constructor with params
    public Price(Long id, String type, Double amount, LocalDate start_date, LocalDate end_date) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    // toString, type, price, start and end date
    @Override
    public String toString() {
        return "Prices{" +
                "type='" + type + '\'' +
                ", price=" + amount +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                '}';
    }
}
