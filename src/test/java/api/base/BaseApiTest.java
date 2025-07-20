package api.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {

    protected RequestSpecification spec;

    @BeforeClass
    public void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        String baseUrl = System.getenv().getOrDefault("API_BASE_URL", "https://fakerestapi.azurewebsites.net");
        RestAssured.baseURI = baseUrl;

        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addRequestSpecification(authentication())
                .build();
    }

    private RequestSpecification authentication() {
        // Return a spec with auth, modify as per actual auth requirements
        return RestAssured.given().auth().preemptive().basic("authUsername", "authPassword");
    }
}
