package ru.kashigin.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kashigin.library.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
