package de.couchkiwi.deskControl.endpoint.rest;

import de.couchkiwi.deskControl.database.DeskControlRepository;
import de.couchkiwi.deskControl.database.Desks;
import de.couchkiwi.deskControl.database.OfficeSpaces;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/office")
public class OfficeSpaceRestController {


    private final DeskControlRepository deskControlRepository;

    @Autowired
    public OfficeSpaceRestController(DeskControlRepository deskControlRepository) {
        this.deskControlRepository = deskControlRepository;
    }

    @GetMapping(value = "/getall", produces = "application/json")
    public ResponseEntity<List<OfficeSpaces>> getAll() throws URISyntaxException {

        Iterable it =  deskControlRepository.findAll();

        if (it == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {

            List<OfficeSpaces> officeSpaces =  IteratorUtils.toList(it.iterator());

            return new ResponseEntity<>(officeSpaces, HttpStatus.OK);
        }


    }

    @GetMapping(value = "/getofficespace/{id}", produces = "application/json")
    public ResponseEntity<OfficeSpaces> getById(@PathVariable("id") long id) throws URISyntaxException {

        Optional<OfficeSpaces> optional = deskControlRepository.findById(id);

        if (optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(value = "/addnewdesk", produces = "application/json")
    public ResponseEntity<String> addNewDesk (@RequestBody Desks desk) {

        Optional<OfficeSpaces> optional = deskControlRepository.findById((long) desk.getOfficeSpaceId());

        if (optional.isPresent()) {
            OfficeSpaces officeSpace = optional.get();

            if(deskControlRepository.countDesks(desk.getDeskNumber(), desk.getOfficeSpaceId()) == 0) {
                officeSpace.getDesksSet().add(desk);
                deskControlRepository.save(officeSpace);

                return new ResponseEntity<>("Desk added!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Desk already exists for given Office!", HttpStatus.CONFLICT);
            }

        } else {
            return new ResponseEntity<>("Office Not Found!", HttpStatus.EXPECTATION_FAILED);
        }

    }

}
