package de.couchkiwi.deskControl.endpoint.rest;

import de.couchkiwi.deskControl.database.DeskControlRepository;
import de.couchkiwi.deskControl.database.Desks;
import de.couchkiwi.deskControl.database.OfficeSpaces;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @PostMapping(value = "/add/desk")
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

    @PostMapping("/add/OfficeSpace")
    public ResponseEntity<String> addNewOfficeSpace(@RequestParam("officename") String officeSpaceName) {

        Optional optional = deskControlRepository.findByOfficeSpaceName(officeSpaceName);
        if (optional.isPresent()) {
            return new ResponseEntity<>("Office-Space already exists", HttpStatus.ALREADY_REPORTED);
        } else {
            OfficeSpaces officeSpace = new OfficeSpaces();
            officeSpace.setOfficeSpaceName(officeSpaceName);
            deskControlRepository.save(officeSpace);

            return new ResponseEntity<>("Office-Space added", HttpStatus.OK);
        }


    }

    @DeleteMapping("/delete/OfficeSpace")
    public ResponseEntity<String> deleteOffice(@RequestParam("id") Long officeSpaceId) {
        deskControlRepository.deleteById(officeSpaceId);

        return new ResponseEntity<>("ID deleted or not existing", HttpStatus.OK);
    }

    @DeleteMapping("/delete/desk")
    public ResponseEntity<String> deleteDesk(@RequestParam("officespaceid") Long officeSpaceId,
                                             @RequestParam("desknumber") String deskNumber) {
        Optional<OfficeSpaces> optional = deskControlRepository.findById(officeSpaceId);

        if (optional.isPresent()) {
            Set<Desks> desks = optional.get().getDesksSet();

            desks.removeIf(desks1 -> desks1.getDeskNumber().equals(deskNumber));

            deskControlRepository.save(optional.get());
        }

        return new ResponseEntity<>("ID deleted or not existing", HttpStatus.OK);
    }

}
