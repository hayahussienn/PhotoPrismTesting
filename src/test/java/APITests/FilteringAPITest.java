
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
    private final String BASE_URL = "http://localhost:2342";
    //private final String BASE_URL = "https://72ec-2a06-c701-78d6-a500-2dfd-69a0-ff4e-a513.ngrok-free.app";
    private final String USERNAME = "Admin";
    private final String PASSWORD = "photoprism";

    // Photo IDs as constants
    private static final String ID_FRANCE_PHOTO1= "pssi8b6xosk1bbdn";
    private static final String ID_FRANCE_PHOTO2 = "psszauvnlzib91l5";
    private static final String ID_2015_PHOTO1 = "pssi8b6xosk1bbdn";
    private static final String ID_2015_PHOTO2 = "psszauvnlzib91l5";
    private static final String ID_2015_PHOTO3= "psszc342djfc2nrq";
    private static final String ID_ANIMAL_PHOTO ="pssi8lus4jl1yeal";
    private static final String ID_RED_PHOTO = "pssi68spcmhbimna";
    private static final String ID_France2015_PHOTO1 ="pssi8b6xosk1bbdn";



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
    public void testFilterPhotosByColorRed() {
        filterPhotosParams("", 0, 0, "","red")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(1)) // Verify that exactly one photo is returned
                .body("UID[0]", equalTo(ID_RED_PHOTO)); // Verify the UID matches
    }

    @Test
    public void testFilterPhotosByCountryFrance() {
        filterPhotosParams("fr", 0, 0, "","")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(2)) // Verify that exactly three photos are returned
                .body("UID", hasItems(ID_FRANCE_PHOTO1,ID_FRANCE_PHOTO2)); // Verify all UIDs match
    }


    @Test
    public void testFilterPhotosByYear2015() {
        filterPhotosParams("", 2015, 0, "","")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(3)) // Verify that exactly two photos are returned
                .body("UID", hasItems(ID_2015_PHOTO1,ID_2015_PHOTO2,ID_2015_PHOTO3)); // Verify both UIDs match
    }

    @Test
    public void testFilterPhotosByCategoryAnimal() {
        filterPhotosParams("", 0, 0, "animal","")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(1)) // Verify that exactly one photo is returned
                .body("UID[0]", equalTo(ID_ANIMAL_PHOTO)); // Verify the UID matches
    }



    @Test
    public void testFilterPhotosByMonthJanuary() {
        filterPhotosParams("", 0, 1, "","")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(0)); // Verify that no photo is returned

    }


    @Test
    public void testFilterPhotosByCountryFranceAndYear2015() {
        filterPhotosParams("fr", 2015, 0, "","")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", hasSize(2)) // Verify that exactly one photo is returned
                .body("UID", hasItems(ID_France2015_PHOTO1,ID_FRANCE_PHOTO2)); // Verify both UIDs match
    }


}

