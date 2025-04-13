package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Representation;
import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Repositories.RepresentationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepresentationService {

    @Autowired
    RepresentationRepository representationRepository;
    private RepresentationRepository RepresentationRepository;

    // CREATE
    public void addRepresentation(Representation representation) {
        representationRepository.save(representation);
    }

    // READ (all)
    public List<Representation> getAll() {
        return representationRepository.findAll();
    }

    // READ (one)
    public Representation getById(Long id) {
        Optional<Representation> opt = representationRepository.findById(id);
        return opt.orElse(null);
    }

    // UPDATE
    public void updateRepresentation(Long id, Representation newData) {
        Representation existing = getById(id);
        if (existing != null) {

            existing.setSchedule(newData.getSchedule());
            existing.setLocation(newData.getLocation());
            existing.setShow(newData.getShow());

            representationRepository.save(existing);
        }

    }


    @Transactional
    public void deleteRepresentation(Long id) {
        Optional<Representation> repOpt = representationRepository.findById(id);
        if (repOpt.isPresent()) {
            Representation rep = repOpt.get();


    //public Representation getRepresentation(long id) {
     //   return RepresentationRepository.findById(id).orElse(null);
    }
}

            // Vérifier si des utilisateurs ont déjà réservé cette représentation
            //if (rep.getUsers() != null && !rep.getUsers().isEmpty()) {

            //    throw new IllegalStateException("La représentation ne peut pas être supprimée car elle a des réservations.");
           // }

//   ?         // Nettoyer la relation avec Show
//            if (rep.getShow() != null) {
//                Show associatedShow = rep.getShow();
//                // Assurez-vous que la collection de représentations dans Show est initialisée
//                // Vous pouvez aussi utiliser une méthode dans l'entité Show pour retirer la représentation
//                associatedShow.getRepresentations().remove(rep);
//                rep.setShow(null);
//            }
            // Si besoin, nettoyer la relation avec le Show
           // if (rep.getShow() != null) {
            //    rep.getShow().getRepresentations().remove(rep);
            //    rep.setShow(null);
          //  }

            // supprimer l'entité Representation
            //representationRepository.delete(rep);
        //} else {
            //throw new EntityNotFoundException("Representation with id " + id + " not found.");
       // }
    //}

}