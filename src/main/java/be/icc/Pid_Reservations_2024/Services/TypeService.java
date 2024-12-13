package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Types;
import be.icc.Pid_Reservations_2024.Repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;

    public List<Types> getAllTypes() {
        List<Types> types = new ArrayList<>();
        typeRepository.findAll().forEach(types::add);
        return types;
    }

    public Types getType(long id){
        Optional<Types> typeId = typeRepository.findById(id);

        return typeId.isPresent() ? typeId.get() : null;
    }

    public void createType(Types type){
        typeRepository.save(type);
    }

    public void updateType(long id, Types type){
        typeRepository.save(type);
    }

    public void deleteType(long id) {
        typeRepository.deleteById(id);
    }

}
