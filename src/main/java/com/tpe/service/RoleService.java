package com.tpe.service;

import com.tpe.domain.Role;
import com.tpe.domain.enums.UserRole;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository; //create Role Repository then inject here .


    // i will create this after set Role in Service Class
    public Role getRoleByType(UserRole roleType) {
        Role role = roleRepository.findByName(roleType).orElseThrow(
                ()->new ResourceNotFoundException("Role is not found")
        );
        return role;
    }
}
