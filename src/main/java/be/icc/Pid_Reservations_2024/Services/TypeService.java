package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Type;
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

    public List<Type> getAllTypes() {
        List<Type> types = new ArrayList<>();
        typeRepository.findAll().forEach(types::add);
        return types;
    }

    public Type getType(long id){
        Optional<Type> typeId = typeRepository.findById(id);

        return typeId.isPresent() ? typeId.get() : null;
    }

    public void createType(Type type){
        typeRepository.save(type);
    }

    public void updateType(long id, Type type){
        typeRepository.save(type);
    }

    public void deleteType(long id) {
        typeRepository.deleteById(id);
    }

}
