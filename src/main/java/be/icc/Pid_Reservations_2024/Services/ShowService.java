package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.ArtisteType;
import be.icc.Pid_Reservations_2024.Models.Location;
import be.icc.Pid_Reservations_2024.Models.Price;
import be.icc.Pid_Reservations_2024.Models.Show;
import be.icc.Pid_Reservations_2024.Repositories.ShowRepository;
import com.github.slugify.Slugify;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    /**
     * Gets a list of shows in pages.
     * <p>
     * This method gets shows from the database in a paginated way.
     * It returns only a part of the shows based on the page number size.
     *
     * @param pageable information about the page( how many shows per page and wich page to get)
     * @return a {@link Page} with a list of {@link Show} objects for the current page.
     */
    public Page<Show> getAllShows(Pageable pageable) {
        return showRepository.findAll(pageable);
    }

    public List<Show> getAll() {
        return showRepository.findAll();
    }

    public List<Show> getByIds(List<Long> ids) {
        List<Integer> integerIds = ids.stream()
                .map(Long::intValue)
                .toList(); // Convertit chaque Long en Integer
        return showRepository.findAllById(integerIds);
    }

    public Show getShow(long id) {
        return showRepository.findById(id).orElse(null);
    }

    public void add(Show show) {
        showRepository.save(show);
    }


    public void update(Long id, Show newShowData) {
        Optional<Show> optionalShow = showRepository.findById(id);
        if (optionalShow.isPresent()) {
            Show existingShow = optionalShow.get();

            // Mettre à jour les champs modifiables
            existingShow.setTitle(newShowData.getTitle());
            existingShow.setPosterUrl(newShowData.getPosterUrl());
            existingShow.setDuration(newShowData.getDuration());
            existingShow.setBookable(newShowData.getBookable());
            existingShow.setLocation(newShowData.getLocation());

            // Si le titre a changé, recalculer le slug
            if (!existingShow.getTitle().equals(newShowData.getTitle())) {
                try {
                    Slugify slg = Slugify.builder().build();
                    existingShow.setSlug(slg.slugify(newShowData.getTitle()));
                } catch (Exception e) {
                    // Gérez l'exception de slugification selon votre logique
                    e.printStackTrace();
                }
            }

            // Sauvegarder l'objet mis à jour
            showRepository.save(existingShow);
        } else {
            throw new EntityNotFoundException("Show with id " + id + " not found.");
        }
    }

    @Transactional
    public void delete(Long id) {
        Optional<Show> showOpt = showRepository.findById(id);
        if (showOpt.isPresent()) {
            Show show = showOpt.get();

            // Nettoyer la relation ManyToMany avec Price
            if (show.getPrices() != null) {
                for (Price p : new ArrayList<>(show.getPrices())) {
                    p.getShows().remove(show);  // retire 'show' côté Price
                }
                show.getPrices().clear();       // vide la collection côté Show
            }

            // Nettoyer la relation ManyToMany avec ArtisteType
            if (show.getArtistTypes() != null) {
                // Créer une copie pour éviter ConcurrentModificationException
                for (ArtisteType at : new ArrayList<>(show.getArtistTypes())) {
                    show.removeArtistType(at);
                }
            }

            // Supprimer le show (suppression en cascade des représentations gérée automatiquement
            // avec configuration cascade = CascadeType.ALL et orphanRemoval = true sur la collection de représentations)
            showRepository.delete(show);

        } else {
            throw new EntityNotFoundException("Show with id " + id + " not found.");
        }
    }


    public List<Show> getFromLocation(Location location) {
        return showRepository.findByLocation(location);
    }

}
