package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Locality;
import be.icc.Pid_Reservations_2024.Repositories.LocalitiesRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LocalitiesService {

    @Autowired
    private LocalitiesRepository localitiesRepository;

    public List<Locality> getAll() {
        java.util.List<Locality> localities = new ArrayList<>();
        localitiesRepository.findAll().forEach(localities::add);
        return localities;
    }

    public Locality getById(long id) {
        return localitiesRepository.findById(id).orElse(null);
    }

    public Locality getLocalityByPostalCode(String postalCode) {
        return localitiesRepository.findByPostalCode(postalCode);
    }

    public Locality getLocality(String locality) {
        return localitiesRepository.findByLocality(locality);
    }

    public void addLocality(Locality locality) {
        localitiesRepository.save(locality);
    }

    public void updateLocality(long id, Locality locality) {
        localitiesRepository.save(locality);
    }

    public void deleteLocality(long id) {
        localitiesRepository.deleteById(id);
    }

}
