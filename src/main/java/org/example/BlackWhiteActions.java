package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class BlackWhiteActions {
    private WebDriver driver;
    private final By photoCardSelector = By.cssSelector("div.media.result.is-photo");
    private final By bwButtonSelector = By.cssSelector("button[aria-label='Convert to Black & White']");
    private final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(20);

    public BlackWhiteActions(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Opens the first available photo in the gallery
     * @return true if photo was successfully opened
     */
    public boolean openPhoto() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            wait.until(ExpectedConditions.presenceOfElementLocated(photoCardSelector));

            WebElement photo = driver.findElements(photoCardSelector).get(3);
            Thread.sleep(3000);
            photo.click();

            // Wait for the photo viewer to open
            wait.until(ExpectedConditions.visibilityOfElementLocated(bwButtonSelector));
            return true;
        } catch (Exception e) {
            System.out.println("Failed to open photo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Clicks the Black & White conversion button
     * @return true if button was clicked successfully
     */
    public boolean clickBlackWhiteButton() {
        try {
            WebElement bwButton = driver.findElement(bwButtonSelector);
            bwButton.click();
            return true;
        } catch (Exception e) {
            System.out.println("Failed to click B/W button: " + e.getMessage());
            return false;
        }
    }

    /**
     * Handles any alert that might appear after clicking the B/W button
     * @return true if no alert appeared or if alert was handled successfully
     */
    public boolean handlePossibleAlert() {
        try {
            WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            Alert alert = alertWait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            System.out.println("Alert appeared with text: " + alertText);
            alert.accept();

            // Try clicking again if alert appeared
            WebElement bwButton = driver.findElement(bwButtonSelector);
            bwButton.click();
            return true;
        } catch (TimeoutException e) {
            // No alert, which is good for the success path
            System.out.println("No alert detected, conversion may be processing...");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to handle alert: " + e.getMessage());
            return false;
        }
    }

    /**
     * Waits for the conversion to complete and for a new window to open
     * @param waitTimeInSeconds how long to wait for conversion
     * @return the new window handle if one was opened, null otherwise
     */
    public String waitForConversionAndSwitchToNewWindow(int waitTimeInSeconds) {
        // Store original window handle
        String originalWindow = driver.getWindowHandle();

        // Wait for the conversion process to complete
        System.out.println("Waiting for black and white conversion to complete...");
        try {
            Thread.sleep(waitTimeInSeconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Check if a new window/tab has opened
        Set<String> windowHandles = driver.getWindowHandles();
        System.out.println("Window count after waiting: " + windowHandles.size());

        // If there are multiple windows, switch to the new one
        if (windowHandles.size() > 1) {
            System.out.println("New window detected! Switching to it...");

            // Switch to the new window (not the original one)
            for (String windowHandle : windowHandles) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    System.out.println("Switched to new window: " + windowHandle);
                    return windowHandle;
                }
            }
        }

        System.out.println("No new window opened after waiting.");
        return null;
    }

    /**
     * Checks if the current window contains a Black & White image heading
     * @return true if heading is found
     */
    public boolean hasBlackWhiteHeading() {
        try {
            WebElement heading = driver.findElement(By.xpath("//h2[contains(text(),'Black & White Image')]"));
            boolean hasHeading = heading.isDisplayed();
            System.out.println("Found 'Black & White Image' heading: " + hasHeading);
            return hasHeading;
        } catch (Exception e) {
            System.out.println("Could not find heading: " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks if the current window contains a converted image
     * @return true if a converted image is found
     */
    public boolean hasConvertedImage() {
        try {
            WebElement image = driver.findElement(By.tagName("img"));
            String imageSrc = image.getAttribute("src");
            System.out.println("Found image with src: " + imageSrc);

            // Check if this is a converted B&W image
            boolean hasConvertedImage = imageSrc.contains("converted_images/bw_");
            System.out.println("Image URL contains converted B&W indicators: " + hasConvertedImage);
            return hasConvertedImage;
        } catch (Exception e) {
            System.out.println("Could not find image: " + e.getMessage());
            return false;
        }
    }
}