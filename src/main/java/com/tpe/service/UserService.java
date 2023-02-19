package com.tpe.service;

import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.domain.enums.UserRole;
import com.tpe.dto.UserRequest;
import com.tpe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; // inject user Repository

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;// before inject we can create RoleService

    public void saveUser(UserRequest userRequest) {
        User user = new User();

        user.setFistName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUserName(userRequest.getUserName());

       // user.setPassword(user.getPassword()); //we cannot save plain text into our DB

        //we are first encoding the password and then set the encoded pass
        String userPassword = userRequest.getPassword();
        String encodedPassword = passwordEncoder.encode(userPassword);
        user.setPassword(encodedPassword);

        // set User Role

        Role role = roleService.getRoleByType(UserRole.ROLE_ADMIN);// creathe this mehtod in roleService
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);//save role go to RoleService create getRoleType


    }
}
