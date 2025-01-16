import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class FilteringAPITest {

    private String accessToken;
    private final String BASE_URL = "http://localhost:2342";
    private final String USERNAME = "Admin";
    private final String PASSWORD = "yourpassword";

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

    private Response filterPhotosParams(String country, Integer year, Integer month, String category, String color)
    {
        return given()
                .header("X-Session-ID", accessToken)
                .queryParam("count", 120)
                .queryParam("merged", true)
                .queryParam("public", true)
                .queryParam("quality", 3)
                .queryParam("country", !country.isEmpty() ? country : "")
                .queryParam("year", year != 0 ? year : 0)
                .queryParam("month", month != 0 ? month : 0)
                .queryParam("label", !category.isEmpty()  ? category : "")
                .queryParam("color", !color.isEmpty() ? color : "")
                .when()
                .get("/api/v1/photos");
    }

    @Test
    public void testFilterPhotosByCountryIsrael() {
        filterPhotosParams("il", 0, 0, "","")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(3)) // Verify that exactly three photos are returned
                .body("UID", hasItems("psosjpp23nehtzzg", "psosj3v4cr54jghq", "psosj2h801jykn1a")); // Verify all UIDs match
    }

    @Test
    public void testFilterPhotosByYear2023() {
        filterPhotosParams("", 2023, 0, "","")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(2)) // Verify that exactly two photos are returned
                .body("UID", hasItems("psot6983kk32dc26", "psosjpp23nehtzzg")); // Verify both UIDs match
    }

    @Test
    public void testFilterPhotosByCategoryNature() {
        filterPhotosParams("", 0, 0, "nature","")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(1)) // Verify that exactly one photo is returned
                .body("UID[0]", equalTo("psot5cs97506oo7c")); // Verify the UID matches
    }

    @Test
    public void testFilterPhotosByColorRed() {
        filterPhotosParams("", 0, 0, "","red")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(1)) // Verify that exactly one photo is returned
                .body("UID[0]", equalTo("psospby5uudwhqz2")); // Verify the UID matches
    }

    @Test
    public void testFilterPhotosByMonthOctober() {
        filterPhotosParams("", 0, 10, "","")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(2)) // Verify that exactly one photo is returned
                .body("UID", hasItems("psot5vm3qxk3f5a1", "psosuc52rcs4fwep")); // Verify both UIDs match
    }
    @Test
    public void testFilterPhotosByCountryGermanyAndYear2018() {
        filterPhotosParams("de", 2018, 0, "","")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(2)) // Verify that exactly one photo is returned
                .body("UID", hasItems("psot5vm3qxk3f5a1", "psot5cs97506oo7c")); // Verify both UIDs match
    }



    }
