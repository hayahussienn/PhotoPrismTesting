import org.example.HomePage;
import org.example.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest
{
    WebDriver driver;
    private static final String baseURL = "http://localhost:2342/library/browse";
    private LoginPage login;
    private HomePage homePage;


    @BeforeEach
    public void setup()
    {
        driver = new ChromeDriver();
        driver.get(baseURL);
        login = new LoginPage(driver);
        homePage=new HomePage(driver);

    }


    @Test
    public void testValidUser()
    {

        HomePage home = login.signInAsValidUser("admin", "yourpassword");
        assertTrue(home.isLoggedInSuccess());
    }

    @Test
    public void testInValidUser()
    {

        LoginPage page = login.signInAsInValidUser("invalid", "1234");
        assertTrue(page.isLoggedInFailed());


    }

    @AfterEach
    public void tearDown()
    {
        driver.quit();
    }

}
