package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Price;
import be.icc.Pid_Reservations_2024.Repositories.PriceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    public List<Price> getAll() {
        // Convertir Iterable en List
        List<Price> prices = new ArrayList<>();
        priceRepository.findAll().forEach(prices::add);
        return prices;
    }

    public Price getOne(Long id) {
        return priceRepository.findById(id).orElse(null);
    }

    public List<Price> getByIds(List<Long> ids) {
        return priceRepository.findAllById(ids);
    }

    public void add(Price amount) {
        priceRepository.save(amount);
    }

    public void update(Long id, Price amount) {
        // Optionnel: vérifier d’abord si l’objet existe
        // Price existing = getOne(id);
        priceRepository.save(amount);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Price> priceOpt = priceRepository.findById(id);
        if (priceOpt.isPresent()) {
            Price price = priceOpt.get();
            // Nettoyer la relation ManyToMany avec les shows
            if (price.getShows() != null) {
                price.getShows().clear();
            }

            priceRepository.delete(price);
        } else {
            throw new EntityNotFoundException("Price with id " + id + " not found.");
        }
    }
}