package org.agoncal.quarkus.microservices.book;

import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;

@Path("/api/books")
public class BookResource {

  @Inject Logger logger;

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response createBook(@FormParam("title") String title, @FormParam("author") String author) {
    Book book = new Book();

    book.isbn13 = "13-something";
    book.title = title;
    book.author = author;
    book.creationDate = Instant.now();

    logger.info("Book created : " + book);
    return Response.status(Response.Status.CREATED).entity(book).build();
  }
}
