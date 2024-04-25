package com.tpe.service;


import com.tpe.domain.Role;
import com.tpe.domain.enums.UserRole;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    public Role findByName(UserRole roleType) {
        Role role = roleRepository.findByName(roleType).orElseThrow(
                () -> new ResourceNotFoundException("Role with name: "+roleType.name()+" is not found in DB!")
        );
        return role;
    }

    @Autowired
    private RoleRepository roleRepository;

}
