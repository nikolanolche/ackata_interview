package api.books;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import api.base.BaseApiTest;

import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class BooksApiTest extends BaseApiTest {

    @Test
    public void test_validateSchemaForAllBooks() {
        given()
                .get("/api/v1/Books")
                .then()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("api/schemas/books_list_schema.json"));
    }

    @Test
    public void test_createBook_validateResponse() throws Exception {
        String jsonBody = Files.readString(
                Paths.get("src/test/resources/api/testData/new_book.json"));

        given(spec)
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/api/v1/Books")
                .then()
                .statusCode(200)
                .body("title", equalTo("New Book Title"))
                .body("pageCount", equalTo(250));
    }

}
