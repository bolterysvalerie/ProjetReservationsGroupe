package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Troupe;
import be.icc.Pid_Reservations_2024.Repositories.TroupeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TroupeService {

    @Autowired
    private TroupeRepository troupeRepository;

    public List<Troupe> findAll() {
        List<Troupe> troupeList = new ArrayList<>();
        troupeRepository.findAll().forEach(troupeList::add);
        return troupeList;
    }

    public Troupe findById(long id) {
        return troupeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Artist not found"));
    }

    public void save(Troupe troupe) {
        troupeRepository.save(troupe);
    }


}
