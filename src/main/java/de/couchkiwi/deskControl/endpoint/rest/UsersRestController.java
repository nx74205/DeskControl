package de.couchkiwi.deskControl.endpoint.rest;

import de.couchkiwi.deskControl.database.UserRepository;
import de.couchkiwi.deskControl.database.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersRestController {

    private final UserRepository userRepository;

    @Autowired
    public UsersRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser (@RequestBody Users user) {

        Optional optional = userRepository.findByUserName(user.getUserName());

        if (optional.isPresent()) {
            return new ResponseEntity<>("User already existing!", HttpStatus.ALREADY_REPORTED);
        } else {
            userRepository.save(user);
            return new ResponseEntity<>("User added!", HttpStatus.OK);
        }
    }
}
