package org.n8.api.service;

import org.n8.api.exception.UserNotFoundException;
import org.n8.api.model.User;
import org.n8.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public User modifyUser(String userId, User updatedUser) {
        return userRepository.findById(userId).map(user -> {
            user.setNombre(updatedUser.getNombre());
            user.setEmail(updatedUser.getEmail());
            user.setTelefono(updatedUser.getTelefono());
            user.setMetodoPago(updatedUser.getMetodoPago());
            user.setRol(updatedUser.getRol());
            return userRepository.save(user);
        }).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

}
