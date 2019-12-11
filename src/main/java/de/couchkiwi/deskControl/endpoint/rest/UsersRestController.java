package de.couchkiwi.deskControl.endpoint.rest;

import de.couchkiwi.deskControl.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UsersRestController {

    private final UserRepository userRepository;
    private final DeskControlRepository deskControlRepository;

    @Autowired
    public UsersRestController(UserRepository userRepository,
                               DeskControlRepository deskControlRepository) {
        this.userRepository = userRepository;
        this.deskControlRepository = deskControlRepository;
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

   @PostMapping("/add/DeskUser")
    public ResponseEntity<String> addNewOfficeSpace(@RequestParam("office") String officeSpaceName,
                                                    @RequestParam("desknumber") String deskNumber,
                                                    @RequestParam("username") String userName) {

        Optional<Users> userOptional = userRepository.findByUserName(userName);
        Optional<Long> optionalLong = deskControlRepository.findByOfficeSpaceName(officeSpaceName);

        if (userOptional.isPresent() && optionalLong.isPresent()) {
            OfficeSpaces office = deskControlRepository.findById(optionalLong.get()).get();

            Desks userDesk = findDeskByDeskNumber(office.getDesksSet(), deskNumber);

            if (userDesk != null) {
                userDesk.setAssignedUserId(userOptional.get().getUserId());
                deskControlRepository.save(office);

                return new ResponseEntity<>("User added to Desk!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Desk not found in given Office!", HttpStatus.OK);
            }

        } else {
            return new ResponseEntity<>("User or Desk not found!", HttpStatus.OK);
        }

    }

    private Desks findDeskByDeskNumber(Set<Desks> desks, String deskNumber) {

        Desks d = desks.stream()
                .filter(userDesk -> deskNumber.equals(userDesk.getDeskNumber()))
                .findFirst()
                .orElse(null);

        return d;

        }

    }