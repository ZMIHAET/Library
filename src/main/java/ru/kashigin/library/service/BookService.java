package ru.kashigin.library.service;

import ru.kashigin.library.dto.BookDTO;
import ru.kashigin.library.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book createBook(Book book);
    Book updateBook(Long id, Book book);
    void deleteBook(Long id);
    Book convertToBook(BookDTO bookDTO);
    void releaseBook(Long id);
    void assignBook(Long id, Long person_id);
}
