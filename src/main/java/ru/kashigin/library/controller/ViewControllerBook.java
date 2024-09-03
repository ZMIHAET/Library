package ru.kashigin.library.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kashigin.library.model.Book;
import ru.kashigin.library.model.Person;
import ru.kashigin.library.service.BookService;
import ru.kashigin.library.service.PersonService;

@Controller
public class ViewControllerBook {
    private final BookService bookService;
    private final PersonService personService;
    @Autowired
    public ViewControllerBook(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping("/books")
    public String viewBooks(Model model){
        model.addAttribute("books", bookService.getAllBooks());
        return "/boo/books";
    }

    @GetMapping("/books/new")
    public String addBookForm(Model model){
        model.addAttribute("book", new Book());
        return "/boo/addBook";
    }

    @PostMapping("/books/new")
    public String addBookSubmit(@ModelAttribute @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "boo/addBook";
        bookService.createBook(book);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}")
    public String viewBook(@PathVariable Long id, Model model){
        Book book = bookService.getBookById(id);
        if (book == null)
            throw new RuntimeException("Book not found");
        model.addAttribute("book", book);
        model.addAttribute("people", personService.getAllPersons());
        return "/boo/bookDetails";
    }

    @PostMapping("/books/{id}/release")
    public String releaseBook(@PathVariable Long id){
        bookService.releaseBook(id);
        return "redirect:/books/" + id;
    }

    @PostMapping("/books/{id}/assign")
    public String assignBook(@PathVariable Long id, @RequestParam Long person_id){
        bookService.assignBook(id, person_id);
        return "redirect:/books/" + id;
    }

    @GetMapping("/books/{id}/edit")
    public String editBookForm(@PathVariable("id") Long id, Model model){
        Book book = bookService.getBookById(id);
        if (book == null)
            throw new RuntimeException("Book not found");
        model.addAttribute("book", book);
        return "/boo/editBook";
    }

    @PostMapping("/books/{id}/edit")
    public String editBookSubmit(@PathVariable("id") Long id, @ModelAttribute @Valid Book book,
                                   BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "/boo/editBook";
        Book existingBook = bookService.getBookById(id);
        if (book == null)
            throw new RuntimeException("Book not found");
        existingBook.setName_of_book(book.getName_of_book());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setYear_of_creation(book.getYear_of_creation());
        bookService.updateBook(id, existingBook);
        return "redirect:/books";
    }

    @PostMapping("/books/{id}")
    public String deleteBook(@PathVariable("id") Long id, @RequestParam("_method") String method) {
        if ("delete".equalsIgnoreCase(method))
            bookService.deleteBook(id);
        return "redirect:/books";
    }
}
