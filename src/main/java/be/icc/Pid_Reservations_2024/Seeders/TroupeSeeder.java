package be.icc.Pid_Reservations_2024.Seeders;
import be.icc.Pid_Reservations_2024.Models.Troupe;
import be.icc.Pid_Reservations_2024.Services.TroupeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class TroupeSeeder {

    @Autowired
    private TroupeService troupeService;

    @PostConstruct
    public void seedTroupes() {
        if (troupeService.getAll().isEmpty()) {
            createTroupes();
        } else {
            System.out.println("Les troupes existent déjà, pas de seed nécessaire.");
        }
    }

    private void createTroupes() {

        Troupe troupe1 = new Troupe("Troupe Comédie", "https://static.vecteezy.com/ti/vecteur-libre/p1/19997981-comedie-et-la-tragedie-theatral-masques-theatre-ou-drame-ecole-logo-conception-symbole-vectoriel.jpg");
        Troupe troupe2 = new Troupe("Troupe Drame", "https://us.123rf.com/450wm/lexlinx/lexlinx2004/lexlinx200400031/144979655-masque-d-%C3%A9motion-simple-en-noir-et-blanc-avec-un-vecteur-triste-et-heureux-pour-le-mod%C3%A8le-de.jpg?ver=6");
        Troupe troupe3 = new Troupe("Troupe Cirque", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9q050UXykomNvnTKrwCNDSeFGhG5OQNY16w&s");
        Troupe troupe4 = new Troupe("Troupe Enfants", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSm3iOhwa8_zO5mTPGXVJEAs0AbHoz6AZvyvA&s");


        troupeService.save(troupe1);
        troupeService.save(troupe2);
        troupeService.save(troupe3);
        troupeService.save(troupe4);

        System.out.println("Seed des troupes effectué avec succès !");
    }
}

