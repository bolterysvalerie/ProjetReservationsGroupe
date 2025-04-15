package be.icc.Pid_Reservations_2024.Services;

import java.util.List;
import java.util.Optional;

import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.icc.Pid_Reservations_2024.Models.Location;
import be.icc.Pid_Reservations_2024.Repositories.LocationRepository;

@Service
public class LocationService {
    @Autowired
    private LocationRepository repository;


    public List<Location> getAll() {
        return repository.findAll();
    }


    public Location get(String id) {
        Long indice = (long) Integer.parseInt(id);
        Optional<Location> location = repository.findById(indice);

        return location.isPresent() ? location.get() : null;
    }


    public void add(Location location) {
        if (location.getDesignation() != null && (location.getSlug() == null || location.getSlug().isEmpty())) {
            Slugify slg = Slugify.builder().build();
            location.setSlug(slg.slugify(location.getDesignation()));
        }

        repository.save(location);
    }


    public void update(String id, Location updatedLocation) {
        Long locationId = Long.parseLong(id);
        Optional<Location> existingLocationOpt = repository.findById(locationId);

        if (existingLocationOpt.isPresent()) {
            Location existingLocation = existingLocationOpt.get();

            // Vérifie si la désignation a changé
            if (!updatedLocation.getDesignation().equals(existingLocation.getDesignation())) {
                Slugify slg = Slugify.builder().build();
                updatedLocation.setSlug(slg.slugify(updatedLocation.getDesignation()));
            } else {
                // Si la désignation n’a pas changé, on garde le même slug
                updatedLocation.setSlug(existingLocation.getSlug());
            }

            // On garde le même ID (important !)
            updatedLocation.setId(existingLocation.getId());

            repository.save(updatedLocation);
        }
    }

    public void delete(String id) {
        Long indice = (long) Integer.parseInt(id);

        repository.deleteById(indice);
    }
}
