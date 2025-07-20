package api.authors;

import org.testng.annotations.Test;
import api.base.BaseApiTest;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class AuthorsApiTest extends BaseApiTest {

    @Test
    public void test_validateSchemaForAllAuthors() {
        given()
                .get("/api/v1/Authors")
                .then()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("api/schemas/authors_list_schema.json"));
    }

}
