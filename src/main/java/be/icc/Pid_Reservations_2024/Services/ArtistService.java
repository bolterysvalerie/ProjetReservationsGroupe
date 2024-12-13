package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Artists;
import be.icc.Pid_Reservations_2024.Repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public List<Artists> getAllArtists() {
        List<Artists> artists = new ArrayList<>();
        artistRepository.findAll().forEach(artists::add);
        return artists;
    }

    public Artists getArtist(long id){
        return artistRepository.findById(id);
    }

    public void addArtist(Artists artist){
        artistRepository.save(artist);
    }

    public void updateArtist(long id, Artists artist){
        artistRepository.save(artist);
    }

    public void deleteArtist(long id){
        artistRepository.deleteById(id);
    }

}
