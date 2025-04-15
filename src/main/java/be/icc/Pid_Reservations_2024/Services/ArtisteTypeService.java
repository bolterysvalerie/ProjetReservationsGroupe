package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.ArtisteType;
import be.icc.Pid_Reservations_2024.Models.Artist;
import be.icc.Pid_Reservations_2024.Models.Type;
import be.icc.Pid_Reservations_2024.Repositories.ArtisteTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtisteTypeService {

    @Autowired
    private ArtisteTypeRepository artisteTypeRepository;

    // Créer un lien
    public ArtisteType create(ArtisteType artisteType) {
        return artisteTypeRepository.save(artisteType);
    }

    // Récupérer tous les liens
    public List<ArtisteType> getAll() {
        return artisteTypeRepository.findAllWithShows();
    }

    // Récupérer un lien par son id
    public ArtisteType getById(Long id) {
        Optional<ArtisteType> opt = artisteTypeRepository.findById(id);
        return opt.orElse(null);
    }

    public ArtisteType getByIdWithShows(Long id) {
        return artisteTypeRepository.findByIdWithShows(id);
    }


    // Nouvelle méthode d'update qui sauvegarde l'objet entier
    public boolean update(ArtisteType artisteType) {
        try {
            artisteTypeRepository.save(artisteType);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Supprimer un lien
    public boolean deleteArtisteType(Long id) {
        ArtisteType existing = getById(id);
        if (existing != null) {
            artisteTypeRepository.delete(existing);
            return true;
        }
        return false;
    }
}

