package ru.kashigin.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kashigin.library.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
