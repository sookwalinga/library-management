package com.library.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
