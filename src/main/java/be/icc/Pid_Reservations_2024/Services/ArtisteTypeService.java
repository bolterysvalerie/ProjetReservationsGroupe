//package be.icc.Pid_Reservations_2024.Services;
//
//import be.icc.Pid_Reservations_2024.Models.ArtisteType;
//import be.icc.Pid_Reservations_2024.Models.Artist;
//import be.icc.Pid_Reservations_2024.Models.Type;
//import be.icc.Pid_Reservations_2024.Repositories.ArtisteTypeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ArtisteTypeService {
//
//    @Autowired
//    private ArtisteTypeRepository artisteTypeRepository;
//
////    public ArtisteType createArtisteType(Artist artist, List<Type> types) {
////        ArtisteType artisteType = new ArtisteType();
////        artisteType.setArtist(artist);
////        artisteType.setType(types.get(0)); // Exemple : associer un seul type pour simplifier
////        artisteType.setShows(null); // Si nécessaire, initialiser les shows
////
////        return artisteTypeRepository.save(artisteType);
////
////    }
//
////    public ArtisteType createArtisteType(Artist artist, List<Type> types) {
////        // Ici, on suppose que types contient au moins un élément
////        ArtisteType artisteType = ArtisteType.create(artist, types.get(0), null);
////        return artisteTypeRepository.save(artisteType);
////    }
//
//    public List<ArtisteType> createArtisteTypes(Artist artist, List<Type> types) {
//        List<ArtisteType> createdLinks = new ArrayList<>();
//        for (Type type : types) {
//            // On crée un lien entre l'artiste et le type, ici sans shows (initialisé à une liste vide)
//            ArtisteType artisteType = ArtisteType.create(artist, type, new ArrayList<>());
//            createdLinks.add(artisteTypeRepository.save(artisteType));
//        }
//        return createdLinks;
//    }
//
//}


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
//    public List<ArtisteType> getAll() {
//        return artisteTypeRepository.findAll();
//    }
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


//    // Mettre à jour un lien existant (on met à jour l'artiste et le type)
//    public boolean updateArtisteType(Long id, Artist artist, Type type) {
//        ArtisteType existing = getById(id);
//        if (existing != null) {
//            existing.setArtist(artist);
//            existing.setType(type);
//            artisteTypeRepository.save(existing);
//            return true;
//        }
//        return false;
//    }

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

