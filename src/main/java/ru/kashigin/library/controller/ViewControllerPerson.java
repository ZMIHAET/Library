package ru.kashigin.library.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kashigin.library.model.Person;
import ru.kashigin.library.service.PersonService;

@Controller
public class ViewControllerPerson {
    private final PersonService personService;

    @Autowired
    public ViewControllerPerson(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/people")
    public String viewPersons(Model model){
        model.addAttribute("persons", personService.getAllPersons());
        return "/per/persons";
    }

    @GetMapping("/people/new")
    public String addPersonForm(Model model){
        model.addAttribute("person", new Person());
        return "/per/addPerson";
    }

    @PostMapping("/people/new")
    public String addPersonSubmit(@ModelAttribute @Valid Person person, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "per/addPerson";
        personService.createPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/people/{id}")
    public String viewPerson(@PathVariable("id") Long id, Model model){
        Person person = personService.getPersonById(id);
        if (person == null)
            throw new RuntimeException("Person not found");
        model.addAttribute("person", person);
        return "/per/personDetails";
    }

    @GetMapping("/people/{id}/edit")
    public String editPersonForm(@PathVariable("id") Long id, Model model){
        Person person = personService.getPersonById(id);
        if (person == null)
            throw new RuntimeException("Person not found");
        model.addAttribute("person", person);
        return "/per/editPerson";
    }

    @PostMapping("/people/{id}/edit")
    public String editPersonSubmit(@PathVariable("id") Long id, @ModelAttribute @Valid Person person,
                                   BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "/per/editPerson";
        Person existingPerson = personService.getPersonById(id);
        if (person == null)
            throw new RuntimeException("Person not found");
        existingPerson.setName(person.getName());
        existingPerson.setYear(person.getYear());
        personService.updatePerson(id, existingPerson);
        return "redirect:/people";
    }

    @PostMapping("/people/{id}")
    public String deletePerson(@PathVariable("id") Long id, @RequestParam("_method") String method) {
        if ("delete".equalsIgnoreCase(method))
            personService.deletePerson(id);
        return "redirect:/people";
    }
}
