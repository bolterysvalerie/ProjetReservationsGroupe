package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Locality;
import be.icc.Pid_Reservations_2024.Repositories.LocalityRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LocalityService {

    @Autowired
    private LocalityRepository localityRepository;

    public List<Locality> getAll() {
        java.util.List<Locality> localities = new ArrayList<>();
        localityRepository.findAll().forEach(localities::add);
        return localities;
    }

    public Locality getById(long id) {
        return localityRepository.findById(id).orElse(null);
    }

    public Locality getLocalityByPostalCode(String postalCode) {
        return localityRepository.findByPostalCode(postalCode);
    }

    public Locality getLocality(String locality) {
        return localityRepository.findByLocality(locality);
    }

    public void addLocality(Locality locality) {
        localityRepository.save(locality);
    }

    public void updateLocality(long id, Locality locality) {
        localityRepository.save(locality);
    }

    public void deleteLocality(long id) {
        localityRepository.deleteById(id);
    }

}
