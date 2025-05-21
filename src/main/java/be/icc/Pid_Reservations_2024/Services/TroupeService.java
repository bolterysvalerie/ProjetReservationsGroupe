package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Troupe;
import be.icc.Pid_Reservations_2024.Repositories.TroupeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TroupeService {
    @Autowired
    private TroupeRepository troupeRepository;
    public List<Troupe> getAllTroupes() { return troupeRepository.findAll(); }
    public Troupe getTroupe(Long id)  { return troupeRepository.findById(id).orElse(null); }
}