
package UITests;

import org.example.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.example.PhotoCollectionUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;

import static org.example.DriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilteringTest
{
    WebDriver driver;
    private static final String baseURL = "http://localhost:2342";
    //private static final String baseURL = "https://6508-2a06-c701-9dff-a900-5819-9ec9-5fbe-58bc.ngrok-free.app";
    private LoginPage login;
    private HomePage homePage;

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
    private static final String ID_FEBRUARY_PHOTO = "psszc342djfc2nrq";







    @BeforeEach
    public void setup()
    {
        driver = getDriver();
        driver.get(baseURL);

        try {
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement visitSiteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Visit Site']")));
            visitSiteButton.click();
        } catch (TimeoutException err) {
            System.out.println("Ngrok warning page was not loaded");
        }

        login = new LoginPage(driver);
        homePage = login.signInAsValidUser(USERNAME, PASSWORD); // Navigate to home page after login
    }


    @Test
    public void testFilterByCountry()
    {
        List<WebElement> photoList = homePage.getFilteringActions().filterByCountry("France");
        assertEquals(photoList.size(), 2);
        List<String> sortedPhotoList = PhotoCollectionUtils.sortIDs(photoList); // Sort IDs to ensure consistent order

        String idOfPhoto1 = sortedPhotoList.get(0);
        String idOfPhoto2 = sortedPhotoList.get(1);


        assertEquals(idOfPhoto1, ID_FRANCE_PHOTO1);
        assertEquals(idOfPhoto2, ID_FRANCE_PHOTO2);

    }

    @Test
    public void testFilterByYear() {
        List<WebElement> photoList = homePage.getFilteringActions().filterByYear(2015);
        assertEquals(photoList.size(), 3);
        List<String> sortedPhotoList = PhotoCollectionUtils.sortIDs(photoList); // Sort IDs to ensure consistent order

        String idOfPhoto1 = sortedPhotoList.get(0);
        String idOfPhoto2 = sortedPhotoList.get(1);
        String idOfPhoto3 = sortedPhotoList.get(2);

        assertEquals(idOfPhoto1, ID_2015_PHOTO1);
        assertEquals(idOfPhoto2, ID_2015_PHOTO2);
        assertEquals(idOfPhoto3, ID_2015_PHOTO3);
    }


    @Test
    public void testFilterByCategory()
    {
        List<WebElement> photoList = homePage.getFilteringActions().filterByCategory("animal");
        assertEquals(photoList.size(), 1);
        List<String> sortedPhotoList = PhotoCollectionUtils.sortIDs(photoList); // Sort IDs to ensure consistent order

        String idOfPhoto1 = sortedPhotoList.get(0);
        assertEquals(idOfPhoto1, ID_ANIMAL_PHOTO);

    }


    @Test
    public void testFilterByColor()
    {
        List<WebElement> photoList=homePage.getFilteringActions().filterByColor("red");
        assertEquals(photoList.size(),1);
        List<String> sortedPhotoList=PhotoCollectionUtils.sortIDs(photoList); //  Sort IDs to ensure consistent order

        String idOfPhoto1=sortedPhotoList.get(0);
        assertEquals(idOfPhoto1,ID_RED_PHOTO);

    }



    @Test
    public void testFilterByMonth()
    {
        List<WebElement> photoList=homePage.getFilteringActions().filterByMonth(2);
        assertEquals(photoList.size(),1);
        List<String> sortedPhotoList=PhotoCollectionUtils.sortIDs(photoList);  // Sort IDs to ensure consistent order

        String idOfPhoto1=sortedPhotoList.get(0);
        assertEquals(idOfPhoto1,ID_FEBRUARY_PHOTO);



    }



    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}



