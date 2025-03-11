
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
    private final String BASE_URL = "http://localhost:2342";
    //private  final String BASE_URL = "https://72ec-2a06-c701-78d6-a500-2dfd-69a0-ff4e-a513.ngrok-free.app";
    private  final String USERNAME = "Admin";
    private  final String PASSWORD = "photoprism";

    // Photo IDs as constants
    private static final String ID_CAT_PHOTO = "pssi8lus4jl1yeal";
    private static final String ID_SEA_PHOTO = "pssi8b6xosk1bbdn";
    private static final String ID_BLUE_PHOTO = "psszc342djfc2nrq";




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
    public void testSearchPhotosByTwoKeywords()
    {
        searchPhotosParams("blue sea")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(2)) // Verify that exactly two photos are returned
                .body("UID", hasItems(ID_SEA_PHOTO,ID_BLUE_PHOTO)) ;// Verify both UIDs are present
    }



    @Test
    public void testSearchPhotosByCatKeyword()
    {
        searchPhotosParams("cat")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(1)) // Verify that exactly two photos are returned
                .body("UID", hasItems(ID_CAT_PHOTO)) ;// Verify both UIDs are present
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

