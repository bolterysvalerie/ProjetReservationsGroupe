package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

    @Data
    @NoArgsConstructor
    @Getter
    @Setter
    @Entity
    @Table(name = "troupes")
    public class Troupe {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(length = 60, nullable = false, unique = true)
        private String name;

        @Column(name = "logo_url", length = 255)
        private String logoUrl;

        @OneToMany(mappedBy = "troupe")
        private List<Artist> artists = new ArrayList<>();

        // getters & setters
    }


