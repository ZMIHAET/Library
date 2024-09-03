package ru.kashigin.library.dto;

import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kashigin.library.model.Book;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;

    @Min(value = 0, message = "Year should be greater than 0")
    private int year;

    private List<Book> books;
}