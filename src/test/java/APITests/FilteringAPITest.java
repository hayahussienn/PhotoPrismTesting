package APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class FilteringAPITest {

    private String accessToken;
    private final String BASE_URL = "https://a41f-2a06-c701-9dff-a900-b552-d01-60f7-378c.ngrok-free.app";
    private final String USERNAME = "Admin";
    private final String PASSWORD = "yourpassword";

    // Photo IDs as constants
    private static final String ID_ISRAEL_PHOTO1 = "psosjpp23nehtzzg";
    private static final String ID_ISRAEL_PHOTO2 = "psosj3v4cr54jghq";
    private static final String ID_ISRAEL_PHOTO3 = "psosj2h801jykn1a";

    private static final String ID_2023_PHOTO1 = "psot6983kk32dc26";
    private static final String ID_2023_PHOTO2 = "psosjpp23nehtzzg";

    private static final String ID_NATURE_PHOTO ="psot5cs97506oo7c";
    private static final String ID_RED_PHOTO = "psospby5uudwhqz2";

    private static final String ID_OCTOBER_PHOTO1 = "psot5vm3qxk3f5a1";
    private static final String ID_OCTOBER_PHOTO2 = "psosuc52rcs4fwep";

    private static final String ID_GERMANY2018_PHOTO1 ="psot5vm3qxk3f5a1";
    private static final String ID_GERMANY2018_PHOTO2 = "psot5cs97506oo7c";



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
                .body("UID", hasItems(ID_ISRAEL_PHOTO1, ID_ISRAEL_PHOTO2, ID_ISRAEL_PHOTO3)); // Verify all UIDs match
    }

    @Test
    public void testFilterPhotosByYear2023() {
        filterPhotosParams("", 2023, 0, "","")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(2)) // Verify that exactly two photos are returned
                .body("UID", hasItems(ID_2023_PHOTO1, ID_2023_PHOTO2)); // Verify both UIDs match
    }

    @Test
    public void testFilterPhotosByCategoryNature() {
        filterPhotosParams("", 0, 0, "nature","")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(1)) // Verify that exactly one photo is returned
                .body("UID[0]", equalTo(ID_NATURE_PHOTO)); // Verify the UID matches
    }

    @Test
    public void testFilterPhotosByColorRed() {
        filterPhotosParams("", 0, 0, "","red")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(1)) // Verify that exactly one photo is returned
                .body("UID[0]", equalTo(ID_RED_PHOTO)); // Verify the UID matches
    }

    @Test
    public void testFilterPhotosByMonthOctober() {
        filterPhotosParams("", 0, 10, "","")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(2)) // Verify that exactly one photo is returned
                .body("UID", hasItems(ID_OCTOBER_PHOTO1, ID_OCTOBER_PHOTO2)); // Verify both UIDs match
    }
    @Test
    public void testFilterPhotosByCountryGermanyAndYear2018() {
        filterPhotosParams("de", 2018, 0, "","")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(2)) // Verify that exactly one photo is returned
                .body("UID", hasItems(ID_GERMANY2018_PHOTO1, ID_GERMANY2018_PHOTO2)); // Verify both UIDs match
    }



    }
