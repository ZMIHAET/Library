package ru.kashigin.library.service.impls;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.kashigin.library.dto.BookDTO;
import ru.kashigin.library.model.Book;
import ru.kashigin.library.model.Person;
import ru.kashigin.library.repository.BookRepository;
import ru.kashigin.library.repository.PersonRepository;
import ru.kashigin.library.service.BookService;

import java.util.List;
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;


    public BookServiceImpl(BookRepository bookRepository, PersonRepository personRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, Book book) {
        if (bookRepository.findById(id).isPresent()){
            bookRepository.deleteById(id);
            return bookRepository.save(book);
        }
        return null;
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book convertToBook(BookDTO bookDTO){
        return modelMapper.map(bookDTO, Book.class);
    }

    @Override
    @Transactional
    public void releaseBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        Person owner = book.getOwner();
        if (owner != null){
            owner.getBooks().remove(book);
            personRepository.save(owner);
        }
        book.setOwner(null);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void assignBook(Long id, Long person_id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        Person person = personRepository.findById(person_id)
                .orElseThrow(() -> new IllegalArgumentException("Person not found"));

        book.setOwner(person);
        bookRepository.save(book);

        person.getBooks().add(book);
        personRepository.save(person);
    }
}
