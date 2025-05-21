package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "show_tags")
@Getter
@Setter
public class Show_Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identifiant unique de la liaison

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false) // Clé étrangère vers l'entité Show
    private Show show;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false) // Clé étrangère vers l'entité Tag
    private Tag tag;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // Horodatage de la création
}

