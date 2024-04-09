package com.example.projetojavaweb.controllers;

import com.example.projetojavaweb.entities.User;
import com.example.projetojavaweb.repositories.UserRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> listUsers(){
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<User>> getId(@PathVariable Long id){
       Optional<User> user = userRepository.findById(id);
        return ResponseEntity.ok(user);
    }
    @GetMapping(value = "/names/")
    @ResponseBody
    public ResponseEntity<List<User>> getForName(@RequestParam(name = "name") String name){

        List<User> user = userRepository.getForName(name.trim().toUpperCase());
        return new ResponseEntity<List<User>>(user,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User u){
        User user = new User();
        user.setName(u.getName());
        user.setAge(u.getAge());
        user = userRepository.save(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(u.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userSelected){
        User user = userRepository.getReferenceById(id);

        user.setName(userSelected.getName());
        user.setAge(userSelected.getAge());
        user = userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping
    public ResponseEntity<String> delUser(@RequestParam Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<String>("Deletado com sucesso", HttpStatus.OK);
    }
}
