import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SearchingAPITest {
    private  String accessToken;
    private  final String BASE_URL = "http://localhost:2342";
    private  final String USERNAME = "Admin";
    private  final String PASSWORD = "yourpassword";

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = BASE_URL;

        // Login and get access token
        accessToken = given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"username\": \"" + USERNAME + "\",\n" +
                        "    \"password\": \"" + PASSWORD + "\"\n" +
                        "}")
                .when()
                .post("/api/v1/session")
                .then()
                .statusCode(200)
                .extract()
                .path("id");
    }

    private Response searchPhotosParams(String keyWord) {
        return given()
                .header("X-Session-ID", accessToken)
                .queryParam("count", 120)
                .queryParam("offset", "")
                .queryParam("merged", true)
                .queryParam("country", "")
                .queryParam("camera", 0)
                .queryParam("lens", 0)
                .queryParam("label", "")
                .queryParam("latlng", "")
                .queryParam("year", "")
                .queryParam("month",  0)
                .queryParam("color", "")
                .queryParam("order", "")
                .queryParam("q", keyWord)
                .queryParam("public", true)
                .queryParam("quality", 3)
                .when()
                .get("/api/v1/photos");
    }

    @Test
    public void testSearchPhotosByCatKeyword()
    {
        searchPhotosParams("cat")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(2)) // Verify that exactly two photos are returned
                .body("UID", hasItems("psosjn9t5iu3hpdt", "psosjpp23nehtzzg")) ;// Verify both UIDs are present
    }
    @Test
    public void testSearchPhotosByTwoKeywords()
    {
        searchPhotosParams("car dog")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(2)) // Verify that exactly two photos are returned
                .body("UID", hasItems("psosoy75flwtf358", "psot5ohml895cwwd")) ;// Verify both UIDs are present
    }
    @Test
    public void testSearchPhotosByInvalidKeyword()
    {
        searchPhotosParams("invalidkeyword")
                .then()
                .statusCode(200) // API should still return 200
                .contentType(ContentType.JSON)
                .body("$", hasSize(0)); // Verify that no photos are returned
    }



}
