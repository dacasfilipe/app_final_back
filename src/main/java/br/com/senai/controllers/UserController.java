package br.com.senai.controllers;

import br.com.senai.models.Users;
import br.com.senai.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/user") //localhost:8080/user
public class UserController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //localhost:8080/user/all
    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    //localhost:8080/user/createUsers
    @PostMapping(value = "/createUsers",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Users createUsers(@RequestBody Users users) {
        // Create a new Users object
        Users newUsers = new Users();
        // Set user properties
        newUsers.setUsername(users.getUsername());
        newUsers.setEmail(users.getEmail());

        // Encode password before saving
        newUsers.setPassword(passwordEncoder.encode(users.getPassword()));

        // Save the user with encrypted password
        return usersRepository.save(newUsers);
    }

    // Update user (similar password encryption logic)
    @PutMapping(value = "/updateUsers",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Users updateUser(@RequestBody Users users) {
        Users getUser = usersRepository.findById(users.getId()).orElseThrow();
        Users updateUsers = new Users();

        updateUsers.setId(users.getId());
        updateUsers.setUsername(users.getUsername());
        updateUsers.setEmail(users.getEmail());

        // Encode password before saving
        updateUsers.setPassword(passwordEncoder.encode(users.getPassword()));

        return usersRepository.save(updateUsers);
    }

    // Delete user (unchanged)
    @DeleteMapping(value = "/deleteUsers/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Users deleteUsers(@PathVariable Long id) {
        Users getUsers = usersRepository.findById(id).orElseThrow();
        usersRepository.delete(getUsers);
        return getUsers;
    }
}
