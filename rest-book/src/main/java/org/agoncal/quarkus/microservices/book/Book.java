package org.agoncal.quarkus.microservices.book;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbProperty;
import java.time.Instant;

public class Book {
    @JsonbProperty("isbn_13")
    public String isbn13;
    public String author;
    public String title;

    @JsonbDateFormat("yyyy/dd/MM")
    @JsonbProperty("creation_date")
    public Instant creationDate;

    @Override
    public String toString() {
        return "Book{" +
                "isbn13='" + isbn13 + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
