package ru.kashigin.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long book_id;

    @NotEmpty(message = "Name of book should not be empty")
    @Size(min = 2, max = 100, message = "Name of book should be between 2 and 100 characters")
    private String name_of_book;

    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 100, message = "Author should be between 2 and 100 characters")
    private String author;

    @Min(value = 0, message = "Year of creation should be greater than 0")
    private int year_of_creation;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;
}