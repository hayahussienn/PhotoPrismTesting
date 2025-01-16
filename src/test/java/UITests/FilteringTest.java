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

public class FilteringTest
{
    WebDriver driver;
    private static final String baseURL = "http://localhost:2342/library/browse";
    private LoginPage login;
    private HomePage homePage;


    // Photo IDs as constants
    private static final String ID_ISRAEL_PHOTO1 = "psosj2h801jykn1a";
    private static final String ID_ISRAEL_PHOTO2 = "psosj3v4cr54jghq";
    private static final String ID_ISRAEL_PHOTO3 = "psosjpp23nehtzzg";

    private static final String ID_2018_PHOTO1 = "psot5cs97506oo7c";
    private static final String ID_2018_PHOTO2 = "psot5vm3qxk3f5a1";

    private static final String ID_ANIMAL_PHOTO1 ="psosjn9t5iu3hpdt";
    private static final String ID_ANIMAL_PHOTO2 = "psosjpp23nehtzzg";
    private static final String ID_ANIMAL_PHOTO3 = "psosoy75flwtf358";

    private static final String ID_RED_PHOTO = "psospby5uudwhqz2";

    private static final String ID_OCTOBER_PHOTO1 = "psosuc52rcs4fwep";
    private static final String ID_OCTOBER_PHOTO2 = "psot5vm3qxk3f5a1";

    private static final String ID_GERMANY2018_PHOTO1 = "psot5cs97506oo7c";
    private static final String ID_GERMANY2018_PHOTO2 ="psot5vm3qxk3f5a1";





    @BeforeEach
    public void setup()
    {
        driver = new ChromeDriver();
        driver.get(baseURL);
        login = new LoginPage(driver);
        homePage = login.signInAsValidUser("admin", "yourpassword"); // Navigate to home page after login
    }


    @Test
    public void testFilterByCountryIsrael()
    {
        List<WebElement> photoList = homePage.getFilteringActions().filterByCountryIsrael();
        assertEquals(photoList.size(), 3);
        List<String> sortedPhotoList = PhotoCollectionUtils.sortIDs(photoList); // Sort IDs to ensure consistent order

        String idOfPhoto1 = sortedPhotoList.get(0);
        String idOfPhoto2 = sortedPhotoList.get(1);
        String idOfPhoto3 = sortedPhotoList.get(2);

        assertEquals(idOfPhoto1, ID_ISRAEL_PHOTO1);
        assertEquals(idOfPhoto2, ID_ISRAEL_PHOTO2);
        assertEquals(idOfPhoto3, ID_ISRAEL_PHOTO3);
    }



    @Test
    public void testFilterByYear2018() {
        List<WebElement> photoList = homePage.getFilteringActions().filterByYear2018();
        assertEquals(photoList.size(), 2);
        List<String> sortedPhotoList = PhotoCollectionUtils.sortIDs(photoList); // Sort IDs to ensure consistent order

        String idOfPhoto1 = sortedPhotoList.get(0);
        String idOfPhoto2 = sortedPhotoList.get(1);

        assertEquals(idOfPhoto1, ID_2018_PHOTO1);
        assertEquals(idOfPhoto2, ID_2018_PHOTO2);
    }


    @Test
    public void testFilterByCategoryAnimal()
    {
        List<WebElement> photoList = homePage.getFilteringActions().filterByCategoryAnimal();
        assertEquals(photoList.size(), 3);
        List<String> sortedPhotoList = PhotoCollectionUtils.sortIDs(photoList); // Sort IDs to ensure consistent order

        String idOfPhoto1 = sortedPhotoList.get(0);
        String idOfPhoto2 = sortedPhotoList.get(1);
        String idOfPhoto3 = sortedPhotoList.get(2);

        assertEquals(idOfPhoto1, ID_ANIMAL_PHOTO1);
        assertEquals(idOfPhoto2, ID_ANIMAL_PHOTO2);
        assertEquals(idOfPhoto3, ID_ANIMAL_PHOTO3);
    }


    @Test
    public void testFilterByColorRed()
    {
        List<WebElement> photoList=homePage.getFilteringActions().filterByColorRed();
        assertEquals(photoList.size(),1);
        List<String> sortedPhotoList=PhotoCollectionUtils.sortIDs(photoList); //  Sort IDs to ensure consistent order

        String idOfPhoto1=sortedPhotoList.get(0);
        assertEquals(idOfPhoto1,ID_RED_PHOTO);

    }



    @Test
    public void testFilterByMonthOctober()
    {
        List<WebElement> photoList=homePage.getFilteringActions().filterByMonthOctober();
        assertEquals(photoList.size(),2);
        List<String> sortedPhotoList=PhotoCollectionUtils.sortIDs(photoList);  // Sort IDs to ensure consistent order

        String idOfPhoto1=sortedPhotoList.get(0);
        String idOfPhoto2=sortedPhotoList.get(1);

        assertEquals(idOfPhoto1,ID_OCTOBER_PHOTO1);
        assertEquals(idOfPhoto2,ID_OCTOBER_PHOTO2);


    }


    @Test
    public void testFilterPhotosByCountryGermanyAndYear2018()
    {
        List<WebElement> photoList=homePage.getFilteringActions().filterPhotosByCountryGermanyAndYear2018();
        assertEquals(photoList.size(),2);
        List<String> sortedPhotoList=PhotoCollectionUtils.sortIDs(photoList);  // Sort IDs to ensure consistent order

        String idOfPhoto1=sortedPhotoList.get(0);
        String idOfPhoto2=sortedPhotoList.get(1);

        assertEquals(idOfPhoto1,ID_GERMANY2018_PHOTO1);
        assertEquals(idOfPhoto2,ID_GERMANY2018_PHOTO2);

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
