package ru.kashigin.library.service;

import ru.kashigin.library.dto.PersonDTO;
import ru.kashigin.library.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> getAllPersons();
    Person getPersonById(Long id);
    Person createPerson(Person person);
    Person updatePerson(Long id, Person person);
    void deletePerson(Long id);
    Person convertToPerson(PersonDTO personDTO);
}
