package com.generation.ecommerce.service;

import com.generation.ecommerce.dto.DirectionsRequest;
import com.generation.ecommerce.model.Directions;
import com.generation.ecommerce.model.Users;
import com.generation.ecommerce.repository.DirectionsRepository;
import com.generation.ecommerce.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final DirectionsRepository directionsRepository;
    @Autowired //inyeccion de dependencias
    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, DirectionsRepository directionsRepository){
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.directionsRepository = directionsRepository;
    }

    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

    public Users getUserById(Long id){
        return usersRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("El usuario con el id " + id + " no se encuentra")
        );
    }

    public Users addUser(Users user){
        Optional<Users> optionalUsers = usersRepository.findByEmail(user.getEmail());
        if(optionalUsers.isPresent())throw  new IllegalArgumentException("El correo ya existe.");
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return usersRepository.save(user);
    }

    public Users addDirectionUser(Long id, DirectionsRequest directionRequest){
        Users user = usersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El usuario con el id " + id + " no se encuentra"));
        Directions direction = new Directions();
        if(directionRequest.getStreet() != null)direction.setStreet(directionRequest.getStreet());
        if(directionRequest.getNeighborhood() != null)direction.setNeighborhood(directionRequest.getNeighborhood());
        if(directionRequest.getZipCode() != null)direction.setZipCode(directionRequest.getZipCode());
        if(directionRequest.getCountry() != null)direction.setCountry(directionRequest.getCountry());
        direction.setUser(user);
        directionsRepository.save(direction);
        return usersRepository.save(user);
    }

    public Users deleteUserById(Long id){
        Optional<Users> optionalUser = usersRepository.findById(id);
        if(optionalUser.isEmpty()) throw new IllegalArgumentException("El usuario con el id " + id + " no se encuentra");
        usersRepository.deleteById(id);
        return optionalUser.get();
    }

    public Users updateUserById(Long id, Users userDetails){
        Optional<Users> optionalUser = usersRepository.findById(id);
        if(optionalUser.isEmpty()) throw new IllegalArgumentException("El usuario con el id " + id + " no se encuentra");
        Users user =  optionalUser.get();

        if(userDetails.getName() != null)user.setName(userDetails.getName());
        if(userDetails.getLastName() != null)user.setLastName(userDetails.getLastName());
        if(userDetails.getEmail() != null)user.setEmail(userDetails.getEmail());
        if(userDetails.getPassword() != null){
            String hashedPassword = passwordEncoder.encode(userDetails.getPassword());
            user.setPassword(hashedPassword);
        };
        return usersRepository.save(user);
    }

    public boolean loginUser(Users user){
        Optional<Users> optionalUser = usersRepository.findByEmail(user.getEmail());
        if(optionalUser.isEmpty())return false;
        return passwordEncoder.matches(user.getPassword(), optionalUser.get().getPassword());
    }


}
