package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Localities;
import be.icc.Pid_Reservations_2024.Repositories.LocalitiesRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LocalitiesService {

    @Autowired
    private LocalitiesRepository localitiesRepository;

    public List<Localities> getAll() {
        java.util.List<Localities> localities = new ArrayList<>();
        localitiesRepository.findAll().forEach(localities::add);
        return localities;
    }

    public Localities getById(long id) {
        return localitiesRepository.findById(id).orElse(null);
    }

    public Localities getLocalityByPostalCode(String postalCode) {
        return localitiesRepository.findByPostalCode(postalCode);
    }

    public Localities getLocality(String locality) {
        return localitiesRepository.findByLocality(locality);
    }

    public void addLocality(Localities locality) {
        localitiesRepository.save(locality);
    }

    public void updateLocality(long id, Localities locality) {
        localitiesRepository.save(locality);
    }

    public void deleteLocality(long id) {
        localitiesRepository.deleteById(id);
    }

}
