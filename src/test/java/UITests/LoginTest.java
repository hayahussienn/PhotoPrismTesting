package UITests;

import org.example.HomePage;
import org.example.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.example.DriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest
{
    WebDriver driver;
    private static final String baseURL = " https://4fdc-2a06-c701-9dff-a900-b552-d01-60f7-378c.ngrok-free.app";
    private LoginPage login;


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
