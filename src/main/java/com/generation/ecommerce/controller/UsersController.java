package com.generation.ecommerce.controller;

import com.generation.ecommerce.model.Users;
import com.generation.ecommerce.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/users/")
public class UsersController { //http://localhost:8080/api/users/
    private final UsersService usersService;
    @Autowired
    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }

    @GetMapping //http://localhost:8080/api/users/
    public List<Users> getAllUsers(){
        return usersService.getAllUsers();
    }

    @GetMapping(path="{userId}")//http://localhost:8080/api/users/2
    public Users getUserById(@PathVariable("userId")Long id){
        return usersService.getUserById(id);
    }

    @PostMapping
    public Users addUser(@RequestBody Users user){
        return usersService.addUser(user);
    }

    @DeleteMapping(path="{userId}")
    public Users deleteUserById(@PathVariable("userId")Long id){
        return usersService.deleteUserById(id);
    }

    @PutMapping(path="{userId}")
    public Users updateUserById(@PathVariable("userId") Long id, @RequestBody Users user){
        return usersService.updateUserById(id,user);
    }

    @PostMapping(path="login")//http://localhost:8080/api/users/login
    public boolean loginUser(@RequestBody Users user){
        return usersService.loginUser(user);
    }


}
