package com.application.BitHub.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.application.BitHub.objects.User;
import com.application.BitHub.misc.MD5;
import com.application.BitHub.repository.ProductRepository;
import com.application.BitHub.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    Integer a = 0;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers(@RequestParam(required = false) String name) {
        try {
            List<User> users = new ArrayList<User>();

            if (name == null)
                userRepository.findAll().forEach(users::add);
            else
                userRepository.findByName(name).forEach(users::add);

            if (users.isEmpty()) {
                return new ResponseEntity<>("[]", HttpStatus.OK);
            }
            for (User user : users) {
                user.setPassword(null);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            // return new ResponseEntity<>("hi", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") long id ) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            userData.get().setPassword(null);
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{}", HttpStatus.NOT_FOUND);
        }
    }

    /*
    "name" : "Aariv",
    "email": "f20220100@hyderabad.bits-pilani.ac.in",
    "password": "password",
    "phone": 89878656,
    "hostel": "VM"
    */
    @PostMapping("/signup")
    public ResponseEntity<Long> createUser(@RequestBody User user) {
        try {
            Integer a = -1;
            Integer b = -2;
            if (!(user.isBitsian(user.getEmail()))) {
                return new ResponseEntity<>(Integer.toUnsignedLong(a), HttpStatus.CONFLICT);
            } else if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return new ResponseEntity<>(Integer.toUnsignedLong(b), HttpStatus.CONFLICT);
            }

            String encodedPass = MD5.getMd5(user.getPassword());

            User _user = userRepository.save(
                    new User(user.getName(), user.getEmail(), encodedPass, user.getPhone(), user.getHostel()));

            _user.setPassword("Not on my watch");
            return new ResponseEntity<Long>(_user.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            Integer a = 0;
            return new ResponseEntity<>(Integer.toUnsignedLong(a), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody User user) {
        try {
            String encodedPass = MD5.getMd5(user.getPassword());
            Optional<User> userData = userRepository.findByEmailAndPassword(user.getEmail(), encodedPass);
            if (userData.isPresent()) {
                userData.get().setPassword(null);

                return new ResponseEntity<>(userData.get().getId(), HttpStatus.OK);
            } else {

                return new ResponseEntity<>(Integer.toUnsignedLong(a), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
