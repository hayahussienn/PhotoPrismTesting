package APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SearchingAPITest {
    private  String accessToken;
    private  final String BASE_URL = "https://377f-2a06-c701-9dff-a900-d4f0-e92f-86d-d5bf.ngrok-free.app";
    private  final String USERNAME = "Admin";
    private  final String PASSWORD = "yourpassword";

    // Photo IDs as constants
    private static final String ID_CAT_PHOTO1 = "psosjn9t5iu3hpdt";
    private static final String ID_CAT_PHOTO2 = "psosjpp23nehtzzg";
    private static final String ID_DOG_PHOTO = "psosoy75flwtf358";
    private static final String ID_CAR_PHOTO = "psot5ohml895cwwd";


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
                .body("UID", hasItems(ID_CAT_PHOTO1, ID_CAT_PHOTO2 )) ;// Verify both UIDs are present
    }
    @Test
    public void testSearchPhotosByTwoKeywords()
    {
        searchPhotosParams("car dog")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(2)) // Verify that exactly two photos are returned
                .body("UID", hasItems(ID_DOG_PHOTO, ID_CAR_PHOTO)) ;// Verify both UIDs are present
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
