package org.agoncal.quarkus.microservices.book;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.startsWith;

@QuarkusTest
public class BookResourceTest {

  @Test
  public void testCreateBook() {
    given()
        .formParam("title", "test")
        .formParam("author", "Luis")
        .when()
        .post("/api/books")
        .then()
        .statusCode(201)
        .body("isbn_13", startsWith("13-"))
        .body("author", startsWith("Luis"))
        .body("creation_date", startsWith("20"));
  }
}
