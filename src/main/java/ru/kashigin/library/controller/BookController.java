package ru.kashigin.library.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kashigin.library.dto.BookDTO;
import ru.kashigin.library.model.Book;
import ru.kashigin.library.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final ModelMapper modelMapper;

    @Autowired
    public BookController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<BookDTO> getAllBooks(){
        return bookService.getAllBooks().stream()
                .map(this::convertToBookDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable Long id){
        return convertToBookDTO(bookService.getBookById(id));
    }

    @PostMapping
    public BookDTO createBook(@RequestBody BookDTO bookDTO){
        Book book = bookService.convertToBook(bookDTO);
        return convertToBookDTO(bookService.createBook(book));
    }

    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO){
        Book book = bookService.convertToBook(bookDTO);
        return convertToBookDTO(bookService.updateBook(id, book));
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }

    private BookDTO convertToBookDTO(Book book){
        return modelMapper.map(book, BookDTO.class);
    }
}
