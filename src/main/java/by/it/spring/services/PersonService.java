package by.it.spring.services;

import by.it.spring.models.Book;
import by.it.spring.models.Person;
import by.it.spring.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PasswordEncoder passwordEncoder;

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PasswordEncoder passwordEncoder, PersonRepository personRepository) {
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person show(int id) {
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Person person) {
       person.setPassword(passwordEncoder.encode(person.getPassword()));
       person.setRole("ROLE_USER");
        personRepository.save(person);
    }

    @Transactional
    public void update(Person updatedPerson) {
        Person person = personRepository.findById(updatedPerson.getId()).get();
        updatedPerson.setRole(person.getRole());
        updatedPerson.setPassword(person.getPassword());
        personRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }

    public List<Book> getBooksByPerson(int id) {
        Person person = personRepository.findById(id).get();
        for (Book book : person.getBooks()) {
            if((new Date().getTime()-book.getCaptureDate().getTime())>864000000)
           book.setExpired(true);
        }
        return person.getBooks();
    }

    public Optional<Person> checkNameUnique(Person person) {
        return personRepository.findByName(person.getName());

    }
}

