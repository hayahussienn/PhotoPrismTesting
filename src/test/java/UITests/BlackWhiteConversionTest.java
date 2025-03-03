
package UITests;

import org.example.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.example.DriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BlackWhiteConversionTest {
    WebDriver driver;
    private static final String baseURL = "http://localhost:2342";
    private LoginPage login;
    private HomePage homePage;

    private final String USERNAME = "Admin";
    private final String PASSWORD = "photoprism";

    @BeforeEach
    public void setup() {
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
    public void testBlackAndWhiteConversionSuccess() {
        // Step 1: Turn a photo into black and white and wait 10 seconds
        String newWindowHandle = homePage.convertImageToBlackAndWhite(10); // 10 seconds wait time

        // Step 2: Make sure a new window opened with our photo
        assertNotNull(newWindowHandle, "A new window should open with the B&W converted image");

        // Step 3: Look for proof that the photo was converted
        BlackWhiteActions blackWhiteActions = homePage.getBlackWhiteActions();

        // Step 4: Check if we can find the heading or the black and white image
        boolean conversionSuccessful = blackWhiteActions.hasBlackWhiteHeading() || blackWhiteActions.hasConvertedImage();

        // step 5: Assert that the conversion was successful
        assertTrue(conversionSuccessful, "The new window should show a B&W converted image with appropriate heading");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            try {
                // If there's any pop-up message, close it
                try {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                } catch (NoAlertPresentException e) {
                    // No alert present, continue
                }
                // Close the web browser
                driver.quit();
            } catch (Exception e) {
                System.out.println("Error during teardown: " + e.getMessage());
            }
        }
    }
}

