package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "Prices")
public class Prices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type", length = 30)
    private String type;
    @Column(name = "price", nullable = false, length = 10, precision = 2)
    private Double price;
    @Column(name = "start_date")
    private LocalDate start_date;
    @Column(name = "end_date")
    private LocalDate end_date;

    // Relation One To Many
    @OneToMany(mappedBy = "price")
    private Set<Representation_Reservation> representation_reservations;

    // Constructor by default
    protected Prices() { }

    // Constructor with params
    public Prices(Long id, String type, Double price, LocalDate start_date, LocalDate end_date) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    // toString, type, price, start and end date
    @Override
    public String toString() {
        return "Prices{" +
                "type='" + type + '\'' +
                ", price=" + price +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                '}';
    }
}
