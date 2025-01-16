package UITests;

import org.example.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.example.PhotoCollectionUtils;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchingTest
{
    WebDriver driver;
    private static final String baseURL = "http://localhost:2342/library/browse";
    private LoginPage login;
    private HomePage homePage;

    // Photo IDs as constants
    private static final String ID_CAT_PHOTO1 = "psosjn9t5iu3hpdt";
    private static final String ID_CAT_PHOTO2 = "psosjpp23nehtzzg";
    private static final String ID_DOG_PHOTO = "psosoy75flwtf358";
    private static final String ID_CAR_PHOTO = "psot5ohml895cwwd";

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.get(baseURL);
        login = new LoginPage(driver);
        homePage = login.signInAsValidUser("admin", "yourpassword"); // Navigate to home page after login
    }


    @Test
    public void testSearchByCatKeyWord() {
        List<WebElement> photoList = homePage.getSearchActions().getListofPhotosByKeyWord("cat");
        assertEquals(photoList.size(), 2);
        List<String> sortedPhotoList = PhotoCollectionUtils.sortIDs(photoList); // Sort IDs to ensure consistent order

        String idOfPhoto1 = sortedPhotoList.get(0);
        String idOfPhoto2 = sortedPhotoList.get(1);

        assertEquals(idOfPhoto1, ID_CAT_PHOTO1);
        assertEquals(idOfPhoto2, ID_CAT_PHOTO2);

    }


    @Test
    public void testSearchBy2KeyWords() {
        List<WebElement> photoList = homePage.getSearchActions().getListofPhotosBySearch2KeyWord("car dog");
        assertEquals(photoList.size(), 2);
        List<String> sortedPhotoList = PhotoCollectionUtils.sortIDs(photoList); // Sort IDs to ensure consistent order

        String idOfPhoto1 = sortedPhotoList.get(0);
        String idOfPhoto2 = sortedPhotoList.get(1);

        assertEquals(idOfPhoto1, ID_DOG_PHOTO);
        assertEquals(idOfPhoto2, ID_CAR_PHOTO);
    }

    @Test
    public void testSearchByInvalidKeyWord() {
        String returnedMessage = homePage.getSearchActions().searchWithInvalidKeyWord("invalidKeyword123");
        String expectedMessage = "No pictures found";
        assertEquals(returnedMessage, expectedMessage);
    }


    @AfterEach
    public void tearDown() {
        driver.quit();
    }

}
