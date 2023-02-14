package by.it.spring.util;

import by.it.spring.services.PersonService;
import by.it.spring.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonService personService;

    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(personService.checkNameUnique((Person) o).isPresent()){
            errors.rejectValue("name", "", "Человек с таким ФИО уже существует");
        }
    }
}
