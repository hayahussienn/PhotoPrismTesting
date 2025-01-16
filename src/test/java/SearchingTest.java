import org.example.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchingTest {
    WebDriver driver;
    private static final String baseURL = "http://localhost:2342/library/browse";
    private LoginPage login;
    private HomePage homePage;
    private PhotoCollection photoCollection;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.get(baseURL);
        login = new LoginPage(driver);
        photoCollection = new PhotoCollection(driver);
        homePage = login.signInAsValidUser("admin", "yourpassword"); // Navigate to home page after login
    }

    @Test
    public void testCountKeywordCatPhotos() {
        List<WebElement> photoList = homePage.getSearchActions().getListofPhotosByKeyWord("cat");
        assertEquals(photoList.size(), 2);
    }

    @Test
    public void testResultOfSearchByCatKeyWord() {
        List<WebElement> photoList = homePage.getSearchActions().getListofPhotosByKeyWord("cat");
        List<String> sortedPhotoList = photoCollection.sortIDs(photoList); // Sort IDs to ensure consistent order

        String idOfPhoto1 = sortedPhotoList.get(0);
        String idOfPhoto2 = sortedPhotoList.get(1);

        assertEquals(idOfPhoto1, "psosjn9t5iu3hpdt");
        assertEquals(idOfPhoto2, "psosjpp23nehtzzg");
    }

    @Test
    public void testCountResultOf2Keywords() {
        List<WebElement> photoList = homePage.getSearchActions().getListofPhotosByKeyWord("car dog");
        assertEquals(photoList.size(), 2);
    }

    @Test
    public void testResultFor2KeywordSearch() {
        List<WebElement> photoList = homePage.getSearchActions().getListofPhotosByKeyWord("car dog");
        List<String> sortedPhotoList = photoCollection.sortIDs(photoList); // Sort IDs to ensure consistent order

        String idOfPhoto1 = sortedPhotoList.get(0);
        String idOfPhoto2 = sortedPhotoList.get(1);

        assertEquals(idOfPhoto1, "psosoy75flwtf358");
        assertEquals(idOfPhoto2, "psot5ohml895cwwd");
    }

    @Test
    public void testSearchWithInvalidKeyWord() {
        String returnedMessage = homePage.getSearchActions().searchWithInvalidKeyWord("invalidKeyword123");
        String expectedMessage = "No pictures found";
        assertEquals(returnedMessage, expectedMessage);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
