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


    public Representation getRepresentation(long id) {
        return representationRepository.findById(id).orElse(null);
    }

//    @Transactional
//    public void deleteRepresentation(Long id) {
//        Optional<Representation> repOpt = representationRepository.findById(id);
//        if (repOpt.isPresent()) {
//            Representation rep = repOpt.get();
//        }
//    }

    @Transactional
    public void deleteRepresentation(Long id) {
        Optional<Representation> repOpt = representationRepository.findById(id);
        if (repOpt.isPresent()) {
            Representation rep = repOpt.get();

            // //Vérifier si des utilisateurs ont déjà réservé cette représentation
            //if (rep.getUsers() != null && !rep.getUsers().isEmpty()) {
            //
            //    throw new IllegalStateException("La représentation ne peut pas être supprimée car elle a des réservations.");
            // }

            // Si nécessaire, retirer l'association avec l'entité Show
            if (rep.getShow() != null) {
                rep.getShow().getRepresentations().remove(rep);
                rep.setShow(null);
            }

            // Supprimer l'entité Representation
            representationRepository.delete(rep);
        } else {
            throw new EntityNotFoundException("Representation with id " + id + " not found.");
        }
    }


}