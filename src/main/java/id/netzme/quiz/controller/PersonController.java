package id.netzme.quiz.controller;

import id.netzme.quiz.dto.PersonDto;
import id.netzme.quiz.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    private final PersonService userService;

    public PersonController(PersonService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/person")
    public ResponseEntity<PersonDto> getPerson(){
        PersonDto personDetail = userService.getPersonDetail();
        if (personDetail == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PersonDto>(personDetail, HttpStatus.OK);
    }
}


