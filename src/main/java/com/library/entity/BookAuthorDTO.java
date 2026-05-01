package com.library.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for the inner join query result
 * between Books and Authors entities.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthorDTO {

    private Long bookId;
    private String bookTitle;
    private String isbn;
    private Integer publicationYear;
    private String genre;
    private Double price;
    private Long authorId;
    private String authorName;
    private String nationality;
}
