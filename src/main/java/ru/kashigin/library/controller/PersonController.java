package ru.kashigin.library.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kashigin.library.dto.PersonDTO;
import ru.kashigin.library.model.Person;
import ru.kashigin.library.service.PersonService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/people")
public class PersonController {
    private final PersonService personService;
    private final ModelMapper modelMapper;
    @Autowired
    public PersonController(PersonService personService, ModelMapper modelMapper) {
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<PersonDTO> getAllPersons(){
        return personService.getAllPersons().stream()
                .map(this::convertToPersonDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonDTO getPersonById(@PathVariable Long id){
        return convertToPersonDTO(personService.getPersonById(id));
    }

    @PostMapping
    public PersonDTO createPerson(@RequestBody PersonDTO personDTO){
        Person person = personService.convertToPerson(personDTO);
        return convertToPersonDTO(personService.createPerson(person));
    }

    @PutMapping("/{id}")
    public PersonDTO updatePerson(@PathVariable Long id, @RequestBody PersonDTO personDTO){
        Person person = personService.convertToPerson(personDTO);
        return convertToPersonDTO(personService.updatePerson(id, person));
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id){
        personService.deletePerson(id);
    }

    private PersonDTO convertToPersonDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
    }
}
