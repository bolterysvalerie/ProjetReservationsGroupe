package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "Reviews")
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "review", columnDefinition = "TEXT")
    private String review;
    @Column(name = "stars", columnDefinition = "TINYINT UNSIGNED")
    private Short stars;
    @Column(name = "validated")
    private Boolean validated;
    @Column(name = "create_at")
    private LocalDateTime create_at;
    @Column(name = "update_ad")
    private LocalDateTime update_ad;

    // Constructor by default
    protected Reviews() {}

}
