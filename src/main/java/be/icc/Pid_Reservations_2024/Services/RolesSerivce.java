package be.icc.Pid_Reservations_2024.Services;

import be.icc.Pid_Reservations_2024.Models.Roles;
import be.icc.Pid_Reservations_2024.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RolesSerivce {

    @Autowired
    private RoleRepository roleRepository;

    public List<Roles> getAllRoles() {
        List<Roles> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);
        return roles;
    }

    public Roles getRoleById(long id) {
        Optional<Roles> roleId = roleRepository.findById(id);
        return roleId.orElse(null);
    }

    public void addRole(Roles role) {
        roleRepository.save(role);
    }

    public void updateRole(long id, Roles role) {
        roleRepository.save(role);
    }

    public void deleteRole(long id) {
        roleRepository.deleteById(id);
    }

}
