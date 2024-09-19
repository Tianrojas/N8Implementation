package org.n8.api.controller;

import org.springframework.web.bind.annotation.RestController;

import org.n8.api.model.User;
import org.n8.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    //curl -X POST "http://localhost:8080/users" -H "Content-Type: application/json" -d "{\"id\":\"rt465ty\",\"nombre\":\"d\",\"email\":\"s\",\"telefono\":\"fds\",\"metodoPago\":\"sdsa\",\"rol\":\"costumer\"}"
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    //curl -X DELETE "http://localhost:8080/users/3725734"
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    // curl -X PUT "http://localhost:8080/users/{id}" -H "Content-Type: application/json" -d "{\"nombre\":\"nuevoNombre\",\"email\":\"nuevoEmail\",\"telefono\":\"nuevoTelefono\",\"metodoPago\":\"nuevoMetodo\",\"rol\":\"nuevoRol\"}"
    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        return userService.modifyUser(id, updatedUser);
    }

}
