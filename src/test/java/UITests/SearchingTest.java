package UITests;

import org.example.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.example.PhotoCollectionUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;

import static org.example.DriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchingTest
{
    WebDriver driver;
    private static final String baseURL = " https://4fdc-2a06-c701-9dff-a900-b552-d01-60f7-378c.ngrok-free.app";
    private LoginPage login;
    private HomePage homePage;

    // Photo IDs as constants
    private static final String ID_CAT_PHOTO1 = "psosjn9t5iu3hpdt";
    private static final String ID_CAT_PHOTO2 = "psosjpp23nehtzzg";
    private static final String ID_DOG_PHOTO = "psosoy75flwtf358";
    private static final String ID_CAR_PHOTO = "psot5ohml895cwwd";

    @BeforeEach
    public void setup() {
        driver =getDriver();
        driver.get(baseURL);
        try {
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement visitSiteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Visit Site']")));
            visitSiteButton.click();
        } catch (TimeoutException err) {
            System.out.println("Ngrok warning page was not loaded");
        }

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
