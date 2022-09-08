package org.agoncal.quarkus.microservices.book;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Instant;

@Path("/api/books")
public class BookResource {

  @Inject Logger logger;

  @RestClient NumberProxy numberProxy;

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Retry(maxRetries = 2, delay = 3000)
  @Fallback(fallbackMethod = "fallbackCreateBook")
  public Response createBook(@FormParam("title") String title, @FormParam("author") String author) {
    Book book = new Book();

    book.isbn13 = numberProxy.generateIsbnNumbers().isbn13;
    book.title = title;
    book.author = author;
    book.creationDate = Instant.now();

    logger.info("Book created : " + book);
    return Response.status(Response.Status.CREATED).entity(book).build();
  }

  public Response fallbackCreateBook(String title, String author) throws FileNotFoundException {
    Book book = new Book();

    book.isbn13 = "Set later";
    book.title = title;
    book.author = author;
    book.creationDate = Instant.now();

    saveBookOnDisk(book);

    logger.warn("Book save on disk : " + book);
    return Response.status(Response.Status.PARTIAL_CONTENT).entity(book).build();
  }

  private void saveBookOnDisk(Book book) throws FileNotFoundException {
    String bookJson = JsonbBuilder.create().toJson(book);
    try (PrintWriter out = new PrintWriter("book-" + Instant.now().toEpochMilli() + ".json")) {
      out.println(bookJson);
    }
  }
}
