package be.icc.Pid_Reservations_2024.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@Table(name = "Roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role", length = 30)
    private String role;

    // Relation One To Many
    @OneToMany(mappedBy = "roles")
    private List<Role_User> role_user;

    // Constructor by default
    protected Roles() { }

    //Constructor with params
    public Roles(String role) {
        super();
        this.role = role;
    }
    public Roles(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    // ToString for role
    @Override
    public String toString() {
        return "Roles{" +
                "role='" + role + '\'' +
                '}';
    }

}
