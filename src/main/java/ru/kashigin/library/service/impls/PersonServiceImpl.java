package ru.kashigin.library.service.impls;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.kashigin.library.dto.PersonDTO;
import ru.kashigin.library.model.Person;
import ru.kashigin.library.repository.PersonRepository;
import ru.kashigin.library.service.PersonService;

import java.util.List;
@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    public PersonServiceImpl(PersonRepository personRepository, ModelMapper modelMapper){
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person getPersonById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person updatePerson(Long id, Person person) {
        if (personRepository.findById(id).isPresent()) {
            personRepository.deleteById(id);
            return personRepository.save(person);
        }
        return null;
    }

    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public Person convertToPerson(PersonDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }

}
