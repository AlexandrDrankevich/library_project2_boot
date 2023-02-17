package by.it.spring.controllers;

import by.it.spring.dto.PersonDTO;
import by.it.spring.models.Person;
import by.it.spring.services.PersonService;
import by.it.spring.util.PersonErrorResponse;
import by.it.spring.util.PersonNotCreatedException;
import by.it.spring.util.PersonNotFoundException;
import by.it.spring.util.PersonValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/people_rest")
public class PersonRestController {

    PersonService personService;
    ModelMapper modelMapper;
    PersonValidator personValidator;

    @Autowired
    public PersonRestController(PersonService personService, ModelMapper modelMapper, PersonValidator personValidator) {
        this.personService = personService;
        this.modelMapper = modelMapper;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public List<PersonDTO> getPeople() {
        return personService.findAll().stream().map(a -> modelMapper.map(a, PersonDTO.class))
                .collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable("id") int id) {
        Person person = personService.show(id);
        if (person == null) {
            throw new PersonNotFoundException("Person with such id was not found");
        }
        return modelMapper.map(person, PersonDTO.class);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> save(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        Person person = modelMapper.map(personDTO, Person.class);
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                stringBuilder.append(fieldError.getField()).append(":")
                        .append(fieldError.getDefaultMessage());
            }
            throw new PersonNotCreatedException(stringBuilder.toString());
        }
        personService.save(person);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException ex) {
        PersonErrorResponse personErrorResponse = new PersonErrorResponse(ex.getMessage());
        return new ResponseEntity<>(personErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException ex) {
        PersonErrorResponse personErrorResponse = new PersonErrorResponse(ex.getMessage());
        return new ResponseEntity<>(personErrorResponse, HttpStatus.NOT_FOUND);
    }


}
