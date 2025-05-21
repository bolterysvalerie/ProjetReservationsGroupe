package be.icc.Pid_Reservations_2024.Services;
import be.icc.Pid_Reservations_2024.Models.Tag;
import be.icc.Pid_Reservations_2024.Repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    /**
     * Récupère tous les tags existants
     *
     * @return une liste de tags
     */
    public List<Tag> getAll() {
        List<Tag> tags = new ArrayList<>();
        tagRepository.findAll().forEach(tags::add);
        return tags;
    }

    /**
     * Trouve un tag par son identifiant
     *
     * @param id l'ID du tag
     * @return un objet Tag s'il existe ou null sinon
     */
    public Tag findById(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);
        return tag.orElse(null);
    }

    /**
     * Trouve un tag par son nom
     *
     * @param name le nom du tag
     * @return un objet Tag s'il existe ou null sinon
     */
    public Tag findByName(String name) {
        Optional<Tag> tag = tagRepository.findByName(name);
        return tag.orElse(null);
    }

    /**
     * Vérifie si un tag existe déjà par son nom
     *
     * @param name le nom du tag
     * @return true s'il existe, sinon false
     */
    public boolean existsByName(String name) {
        return tagRepository.existsByName(name);
    }

    /**
     * Sauvegarde ou met à jour un tag dans la base de données
     *
     * @param tag le tag à sauvegarder
     * @return le tag sauvegardé
     */
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    /**
     * Supprime un tag grâce à son ID
     *
     * @param id l'ID du tag à supprimer
     */
    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }

    /**
     * Supprime un tag donné
     *
     * @param tag le tag à supprimer
     */
    public void delete(Tag tag) {
        tagRepository.delete(tag);
    }
}

