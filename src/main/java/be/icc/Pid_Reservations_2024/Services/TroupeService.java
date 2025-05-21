package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Troupe;
import be.icc.Pid_Reservations_2024.Repositories.TroupeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TroupeService {

    private final TroupeRepository troupeRepository;

    @Autowired
    public TroupeService(TroupeRepository troupeRepository) {
        this.troupeRepository = troupeRepository;
    }

    /**
     * Récupérer toutes les troupes.
     *
     * @return Liste des troupes
     */
    public List<Troupe> getAll() {
        return troupeRepository.findAll();
    }

    /**
     * Trouver une troupe par son ID.
     *
     * @param id ID de la troupe
     * @return Une option contenant la troupe, si elle existe
     */
    public Troupe findById(Long id) {
        return troupeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Troupe non trouvée avec l'ID : " + id));
    }

    /**
     * Trouver une troupe par son nom.
     *
     * @param name Nom de la troupe
     * @return Une option contenant la troupe, si elle existe
     */
    public Optional<Troupe> findByName(String name) {
        return troupeRepository.findByName(name);
    }

    /**
     * Créer ou mettre à jour une troupe.
     *
     * @param troupe La troupe à enregistrer
     * @return La troupe enregistrée
     */
    public Troupe save(Troupe troupe) {
        return troupeRepository.save(troupe);
    }

    /**
     * Supprimer une troupe par son ID.
     *
     * @param id ID de la troupe à supprimer
     */
    public void delete(Long id) {
        Troupe troupe = findById(id); // Vérifier l'existence
        troupeRepository.delete(troupe);
    }
}

