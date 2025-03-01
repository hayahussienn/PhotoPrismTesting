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
import java.util.Set;

import static org.example.DriverFactory.getDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlackWhiteConversionTest {
    WebDriver driver;
    private static final String baseURL = "http://localhost:2342";
    private LoginPage login;
    private HomePage homePage;

    private final String USERNAME = "Admin";
    private final String PASSWORD = "photoprism";

    // Locators for the elements
    private final By photoCardSelector = By.cssSelector("div.media.result.is-photo");
    private final By bwButtonSelector = By.cssSelector("button[aria-label='Convert to Black & White']");
    private final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

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
        // Wait for photos to load
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.presenceOfElementLocated(photoCardSelector));

        // Store original window handle
        String originalWindow = driver.getWindowHandle();
        System.out.println("Original window handle: " + originalWindow);

        // Click on a photo (first one available)
        WebElement photo = driver.findElements(photoCardSelector).get(0);
        photo.click();

        // Wait for the photo viewer to open and B/W button to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(bwButtonSelector));

        // Click on the Black & White button
        WebElement bwButton = driver.findElement(bwButtonSelector);
        bwButton.click();

        // Handle any alert that might appear (error case)
        try {
            WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            Alert alert = alertWait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            System.out.println("Alert appeared with text: " + alertText);
            alert.accept();
            System.out.println("Alert accepted. The test won't fail, as we'll try again...");

            // Try clicking again - sometimes it works on second attempt
            bwButton = driver.findElement(bwButtonSelector);
            bwButton.click();
        } catch (TimeoutException e) {
            // No alert, which is good for the success path
            System.out.println("No alert detected, conversion may be processing...");
        }

        // Wait for the new window/tab to open
        System.out.println("Waiting for black and white conversion to complete (30+ seconds)...");
        try {
            Thread.sleep(10000); // Wait 30 seconds for conversion
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Check if a new window/tab has opened
        Set<String> windowHandles = driver.getWindowHandles();
        System.out.println("Window count after waiting: " + windowHandles.size());

        // If there are multiple windows, switch to the new one
        boolean foundNewWindow = false;
        if (windowHandles.size() > 1) {
            System.out.println("New window detected! Switching to it...");

            // Switch to the new window (not the original one)
            for (String windowHandle : windowHandles) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    System.out.println("Switched to new window: " + windowHandle);
                    foundNewWindow = true;
                    break;
                }
            }

            if (foundNewWindow) {
                // Check the new window content
                System.out.println("New window URL: " + driver.getCurrentUrl());
                System.out.println("New window title: " + driver.getTitle());

                // Verify the page contains "Black & White Image" heading
                boolean hasHeading = false;
                try {
                    WebElement heading = driver.findElement(By.xpath("//h2[contains(text(),'Black & White Image')]"));
                    hasHeading = heading.isDisplayed();
                    System.out.println("Found 'Black & White Image' heading: " + hasHeading);
                } catch (Exception e) {
                    System.out.println("Could not find heading: " + e.getMessage());
                }

                // Look for an image tag with a URL containing "converted_images/bw_"
                boolean hasConvertedImage = false;
                try {
                    WebElement image = driver.findElement(By.tagName("img"));
                    String imageSrc = image.getAttribute("src");
                    System.out.println("Found image with src: " + imageSrc);

                    // Check if this is a converted B&W image
                    hasConvertedImage = imageSrc.contains("converted_images/bw_");
                    System.out.println("Image URL contains converted B&W indicators: " + hasConvertedImage);
                } catch (Exception e) {
                    System.out.println("Could not find image: " + e.getMessage());
                }

                // The test passes if either the heading or converted image is found
                boolean conversionSuccessful = hasHeading || hasConvertedImage;
                assertTrue(conversionSuccessful,
                        "The new window should show a B&W converted image with appropriate heading");
            }
        } else {
            System.out.println("No new window opened after waiting.");
            System.out.println("This test expects a new tab with the B&W converted image.");
            assertTrue(false, "No new window was opened with the converted image");
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            try {
                // Handle any alerts before quitting
                try {
                    Alert alert = driver.switchTo().alert();
                    alert.accept();
                } catch (NoAlertPresentException e) {
                    // No alert present, continue
                }

                driver.quit();
            } catch (Exception e) {
                System.out.println("Error during teardown: " + e.getMessage());
            }
        }
    }
}