import org.example.FilteringActions;
import org.example.HomePage;
import org.example.LoginPage;
import org.example.PhotoCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilteringTest
{
    WebDriver driver;
    private static final String baseURL = "http://localhost:2342/library/browse";
    private LoginPage login;
    private FilteringActions filter;
    private HomePage homePage;
    private PhotoCollection photoCollection;



    @BeforeEach
    public void setup()
    {
        driver = new ChromeDriver();
        driver.get(baseURL);
        login = new LoginPage(driver);
        filter=new FilteringActions(driver);
        homePage=new HomePage(driver);
        photoCollection = new PhotoCollection(driver);
        HomePage home = login.signInAsValidUser("admin", "yourpassword");


    }


    @Test
    public void testFilterByCountryIsrael()
    {
        List<WebElement> photoList=filter.filterByCountryIsrael();
        assertEquals(photoList.size(),3);

    }

    @Test
    public void testResultOfFilterByCountryIsrael()
    {
        List<WebElement> photoList=filter.filterByCountryIsrael();
        List<String> sortedPhotoList=photoCollection.sortIDs(photoList); // // Sort IDs to ensure consistent order


        String idOfPhoto1=sortedPhotoList.get(0);
        String idOfPhoto2=sortedPhotoList.get(1);
        String idOfPhoto3=sortedPhotoList.get(2);


        assertEquals(idOfPhoto1,"psosj2h801jykn1a");
        assertEquals(idOfPhoto2,"psosj3v4cr54jghq");
        assertEquals(idOfPhoto3,"psosjpp23nehtzzg");

    }

    @Test
    public void testFilterByYear2018()
    {
        List<WebElement> photoList=filter.filterByYear2018();
        assertEquals(photoList.size(),2);

    }
    @Test
    public void testResultOfFilterByYear2018()
    {
        List<WebElement> photoList=filter.filterByYear2018();
        List<String> sortedPhotoList=photoCollection.sortIDs(photoList); // // Sort IDs to ensure consistent order


        String idOfPhoto1=sortedPhotoList.get(0);
        String idOfPhoto2=sortedPhotoList.get(1);


        assertEquals(idOfPhoto1,"psot5cs97506oo7c");
        assertEquals(idOfPhoto2,"psot5vm3qxk3f5a1");


    }

    @Test
    public void testFilterByCategoryAnimal()
    {
        List<WebElement> photoList=filter.filterByCategoryAnimal();
        assertEquals(photoList.size(),3);

    }

    @Test
    public void testResultOfFilterByCategoryAnimal()
    {
        List<WebElement> photoList=filter.filterByCategoryAnimal();
        List<String> sortedPhotoList=photoCollection.sortIDs(photoList); // // Sort IDs to ensure consistent order


        String idOfPhoto1=sortedPhotoList.get(0);
        String idOfPhoto2=sortedPhotoList.get(1);
        String idOfPhoto3=sortedPhotoList.get(2);


        assertEquals(idOfPhoto1,"psosjn9t5iu3hpdt");
        assertEquals(idOfPhoto2,"psosjpp23nehtzzg");
        assertEquals(idOfPhoto3,"psosoy75flwtf358");

    }

    @Test
    public void testFilterByColorRed()
    {
        List<WebElement> photoList=filter.filterByColorRed();
        assertEquals(photoList.size(),1);

    }

    @Test
    public void testResultOfFilterByColorRed()
    {
        List<WebElement> photoList=filter.filterByColorRed();
        List<String> sortedPhotoList=photoCollection.sortIDs(photoList); // // Sort IDs to ensure consistent order

        String idOfPhoto1=sortedPhotoList.get(0);
        assertEquals(idOfPhoto1,"psospby5uudwhqz2");


    }

    @Test
    public void testFilterByMonthOctober()
    {
        List<WebElement> photoList=filter.filterByMonthOctober();
        assertEquals(photoList.size(),2);

    }

    @Test
    public void testResultOfFilterByMonthOctober()
    {
        List<WebElement> photoList=filter.filterByMonthOctober();
        List<String> sortedPhotoList=photoCollection.sortIDs(photoList); // // Sort IDs to ensure consistent order

        String idOfPhoto1=sortedPhotoList.get(0);
        String idOfPhoto2=sortedPhotoList.get(1);

        assertEquals(idOfPhoto1,"psosuc52rcs4fwep");
        assertEquals(idOfPhoto2,"psot5vm3qxk3f5a1");

    }


    @Test
    public void testFilterPhotosByCountryGermanyAndYear2018()
    {

        List<WebElement> photoList=filter.filterPhotosByCountryGermanyAndYear2018();
        assertEquals(photoList.size(),2);

    }

    @Test
    public void testResultOfFilterByGermanyAnd2018()
    {
        List<WebElement> photoList=filter.filterPhotosByCountryGermanyAndYear2018();
        List<String> sortedPhotoList=photoCollection.sortIDs(photoList); // // Sort IDs to ensure consistent order

        String idOfPhoto1=sortedPhotoList.get(0);
        String idOfPhoto2=sortedPhotoList.get(1);

        assertEquals(idOfPhoto1,"psot5cs97506oo7c");
        assertEquals(idOfPhoto2,"psot5vm3qxk3f5a1");

    }
    @AfterEach
    public void tearDown()
    {
        driver.quit();
    }

}
